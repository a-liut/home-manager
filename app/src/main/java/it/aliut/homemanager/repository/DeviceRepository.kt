package it.aliut.homemanager.repository

import it.aliut.homemanager.model.Device
import it.aliut.homemanager.net.HomeManagerApi

class DeviceRepository(private val homeManagerApi: HomeManagerApi) {

    suspend fun getAll(): List<Device> {
        return homeManagerApi.getAllDevices()
    }

    suspend fun getById(id: String): Device {
        return homeManagerApi.getDevice(id)
    }

}