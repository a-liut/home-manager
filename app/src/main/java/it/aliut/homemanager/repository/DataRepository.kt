package it.aliut.homemanager.repository

import it.aliut.homemanager.model.Data
import it.aliut.homemanager.net.HomeManagerApi

class DataRepository(private val homeManagerApi: HomeManagerApi) {
    suspend fun getAll(deviceId: String): List<Data> {
        return homeManagerApi.getDeviceData(
            deviceId = deviceId,
            dataName = null,
            limit = null
        )
    }

    suspend fun getByName(deviceId: String, name: String? = null, limit: Int? = null): List<Data> {
        return homeManagerApi.getDeviceData(
            deviceId = deviceId,
            dataName = name,
            limit = limit
        )
    }
}