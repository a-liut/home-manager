package it.aliut.homemanager.net

import it.aliut.homemanager.model.Data
import it.aliut.homemanager.model.Device
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeManagerApi {
    @GET("devices")
    suspend fun getAllDevices(): List<Device>

    @GET("devices/{deviceId}")
    suspend fun getDevice(@Path("deviceId") deviceId: String): Device

    @GET("devices/{deviceId}/data")
    suspend fun getAllDeviceData(@Path("deviceId") deviceId: String): List<Data>

    @GET("devices/{deviceId}/data/{dataName}")
    suspend fun getDeviceDataByName(
        @Path("deviceId") deviceId: String, @Path("dataName") dataName: String, @Path(
            "limit"
        ) limit: Int
    ): List<Data>
}