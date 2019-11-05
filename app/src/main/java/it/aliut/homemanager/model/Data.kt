package it.aliut.homemanager.model

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("value")
    val value: String,
    @SerializedName("unit")
    val unit: Boolean
) {
}