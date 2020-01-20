package it.aliut.homemanager.ui.devicedetails

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import it.aliut.homemanager.datasource.DeviceDataDataSourceFactory
import it.aliut.homemanager.repository.DataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import java.util.*

class DeviceDetailsViewModel(
    id: String,
    dataRepository: DataRepository
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val deviceDataDataSourceFactory = DeviceDataDataSourceFactory(id, dataRepository, scope)

    private val config = PagedList.Config.Builder()
        .setInitialLoadSizeHint(25)
        .setPageSize(25)
        .setEnablePlaceholders(false)
        .build()

    val devicesData = LivePagedListBuilder(deviceDataDataSourceFactory, config).build()

    val state = Transformations.switchMap(deviceDataDataSourceFactory.source) { it.state }

    val lastUpdate = Transformations.map(deviceDataDataSourceFactory.source) {
        Calendar.getInstance().timeInMillis
    }

    override fun onCleared() {
        super.onCleared()

        scope.coroutineContext.cancel()
    }

    fun refresh() {
        deviceDataDataSourceFactory.getSource()?.refresh()
    }
}
