package com.ab.an.data.network.dto

import com.google.gson.annotations.SerializedName

data class ProfilePictureResponseDto(
    @SerializedName("message")
    val message: String,
    @SerializedName("imageUrl")
    val imageUrl: String
)