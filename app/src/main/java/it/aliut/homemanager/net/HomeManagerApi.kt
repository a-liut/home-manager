package it.aliut.homemanager.net

import it.aliut.homemanager.model.Data
import it.aliut.homemanager.model.Device
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeManagerApi {
    @GET("devices")
    suspend fun getAllDevices(): List<Device>

    @GET("devices/{deviceId}")
    suspend fun getDevice(@Path("deviceId") deviceId: String): Device

    @GET("data")
    suspend fun getDeviceData(
        @Query("deviceId") deviceId: String?,
        @Query("dataName") dataName: String?,
        @Query("limit") limit: Int?
    ): List<Data>
}