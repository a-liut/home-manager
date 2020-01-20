package it.aliut.homemanager.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import it.aliut.homemanager.model.Data
import it.aliut.homemanager.repository.DataRepository
import kotlinx.coroutines.CoroutineScope


class DeviceDataDataSourceFactory(
    private val deviceId: String,
    private val repository: DataRepository,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Data>() {

    val source = MutableLiveData<DeviceDataDataSource>()

    override fun create(): DataSource<Int, Data> {
        val ds = DeviceDataDataSource(deviceId, repository, scope)
        source.postValue(ds)
        return ds
    }

    fun getSource() = source.value

}