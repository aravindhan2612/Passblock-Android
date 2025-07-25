package com.ab.an.data.network.dto


import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("fullName")
    val fullName: String? = null ,
    @SerializedName("password")
    val password: String? = null
)