package it.aliut.homemanager.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import it.aliut.homemanager.model.Device
import it.aliut.homemanager.repository.DeviceRepository
import kotlinx.coroutines.CoroutineScope


class DeviceDataSourceFactory(
    private val repository: DeviceRepository,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Device>() {

    val source = MutableLiveData<DeviceDataSource>()

    override fun create(): DataSource<Int, Device> {
        val ds = DeviceDataSource(repository, scope)
        source.postValue(ds)
        return ds
    }

}