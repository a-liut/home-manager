package it.aliut.homemanager.model

import com.google.gson.annotations.SerializedName

data class Device(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("data")
    val data: List<String>,
    @SerializedName("online")
    val online: Boolean,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("picture_url")
    val pictureUrl: String?
) {
}