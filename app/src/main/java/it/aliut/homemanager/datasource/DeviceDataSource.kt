package it.aliut.homemanager.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import it.aliut.homemanager.model.Device
import it.aliut.homemanager.net.RequestState
import it.aliut.homemanager.repository.DeviceRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class DeviceDataSource(
    private val repository: DeviceRepository,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Device>() {

    val supervisorJob = SupervisorJob()

    val state = MutableLiveData<RequestState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Device>
    ) {
        scope.launch(provideErrorHandler() + supervisorJob) {
            val devices = fetchDevices()

            callback.onResult(devices, null, null)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Device>) {
        // Do nothing
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Device>) {
        // Do nothing
    }

    override fun invalidate() {
        super.invalidate()

        supervisorJob.cancel()
    }

    private suspend fun fetchDevices(): List<Device> {
        state.postValue(RequestState.RUNNING)

        val devices = repository.getAll()

        state.postValue(RequestState.SUCCESS)

        return devices
    }

    private fun provideErrorHandler() = CoroutineExceptionHandler { _, t ->
        Log.e(this.javaClass.simpleName, t.message, t)
        state.postValue(RequestState.ERROR)
    }
}