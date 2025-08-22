package com.ab.an.data.network.dto

import com.google.gson.annotations.SerializedName

data class AuthResponseDto(
    @SerializedName("user")
    val user: UserDto,
    @SerializedName("token")
    val token: String
)