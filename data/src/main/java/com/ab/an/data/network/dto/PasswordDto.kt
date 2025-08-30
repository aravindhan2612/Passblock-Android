package com.ab.an.data.network.dto


import com.google.gson.annotations.SerializedName


data class PasswordDto(
    @SerializedName("_id")
    val id: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("password")
    val password: String? = "",
    @SerializedName("tag")
    val tag: String? = "",
    @SerializedName("usernameOrUserId")
    val usernameOrUserId: String? = "",
    @SerializedName("websiteLink")
    val websiteLink: String? = "",
    @SerializedName("faviconUrl")
    val faviconUrl : String? = "",
    @SerializedName("userId")
    val userId: String
)