package com.ab.an.data.network.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePasswordDto(
    @SerializedName("currentPassword")
    val currentPassword: String? = null,
    @SerializedName("newPassword")
    val newPassword: String? = null,
    @SerializedName("message")
    val responseMessage:String? = null
)