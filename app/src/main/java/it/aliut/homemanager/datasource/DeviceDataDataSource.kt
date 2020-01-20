package it.aliut.homemanager.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import it.aliut.homemanager.model.Data
import it.aliut.homemanager.net.RequestState
import it.aliut.homemanager.repository.DataRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class DeviceDataDataSource(
    private val deviceId: String,
    private val repository: DataRepository,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Data>() {

    val supervisorJob = SupervisorJob()

    val state = MutableLiveData<RequestState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Data>
    ) {
        scope.launch(provideErrorHandler() + supervisorJob) {
            val deviceData = fetchData(deviceId)

            callback.onResult(deviceData, null, null)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Data>) {
        // Do nothing
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Data>) {
        // Do nothing
    }

    override fun invalidate() {
        super.invalidate()

        supervisorJob.cancel()
    }

    fun refresh() {
        this.invalidate()
    }

    private suspend fun fetchData(deviceId: String): List<Data> {
        state.postValue(RequestState.RUNNING)

        val data = repository.getAll(deviceId)

        state.postValue(RequestState.SUCCESS)

        return data
    }

    private fun provideErrorHandler() = CoroutineExceptionHandler { _, t ->
        Log.e(this.javaClass.simpleName, t.message, t)
        state.postValue(RequestState.ERROR)
    }

}