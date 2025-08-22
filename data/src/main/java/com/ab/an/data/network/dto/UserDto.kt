package com.ab.an.data.network.dto


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("fullName")
    val fullName: String? = null ,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("phoneNumber")
    val phoneNumber: String? = null,
    @SerializedName("dateOfBirth")
    val dob: String? = null,
    @SerializedName("profilePictureName")
    val profilePicture: String? = null
)