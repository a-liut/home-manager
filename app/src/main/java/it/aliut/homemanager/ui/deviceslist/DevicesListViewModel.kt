package it.aliut.homemanager.ui.deviceslist

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import it.aliut.homemanager.datasource.DeviceDataSourceFactory
import it.aliut.homemanager.repository.DeviceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class DevicesListViewModel(
    deviceRepository: DeviceRepository
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val deviceDataSourceFactory = DeviceDataSourceFactory(deviceRepository, scope)
    private val config = PagedList.Config.Builder()
        .setInitialLoadSizeHint(25)
        .setPageSize(25)
        .setEnablePlaceholders(false)
        .build()

    val devices = LivePagedListBuilder(deviceDataSourceFactory, config).build()

    val state = Transformations.switchMap(deviceDataSourceFactory.source) { it.state }

    override fun onCleared() {
        super.onCleared()

        scope.coroutineContext.cancel()
    }
}
