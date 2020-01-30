package it.aliut.homemanager.ui.devicedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import it.aliut.homemanager.datasource.DeviceDataDataSourceFactory
import it.aliut.homemanager.model.Device
import it.aliut.homemanager.repository.DataRepository
import it.aliut.homemanager.repository.DeviceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class DeviceDetailsViewModel(
    id: String,
    dataRepository: DataRepository,
    deviceRepository: DeviceRepository
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val deviceDataDataSourceFactory = DeviceDataDataSourceFactory(id, dataRepository, scope)

    private val config = PagedList.Config.Builder()
        .setInitialLoadSizeHint(25)
        .setPageSize(25)
        .setEnablePlaceholders(false)
        .build()

    private val _device = MutableLiveData<Device>()
    val device: LiveData<Device>
        get() = _device

    val devicesData = LivePagedListBuilder(deviceDataDataSourceFactory, config).build()

    val state = Transformations.switchMap(deviceDataDataSourceFactory.source) { it.state }

    init {
        scope.launch {
            _device.postValue(deviceRepository.getById(id))
        }
    }

    override fun onCleared() {
        super.onCleared()

        scope.coroutineContext.cancel()
    }
}
