package it.aliut.homemanager.repository

import it.aliut.homemanager.model.Data
import it.aliut.homemanager.model.Device
import it.aliut.homemanager.net.HomeManagerApi

class DataRepository(private val homeManagerApi: HomeManagerApi) {
    suspend fun getAll(device: Device): List<Data> {
        return homeManagerApi.getAllDeviceData(deviceId = device.id)
    }

    suspend fun getByName(device: Device, name: String, limit: Int = 0): List<Data> {
        return homeManagerApi.getDeviceDataByName(
            deviceId = device.id,
            dataName = name,
            limit = limit
        )
    }
}