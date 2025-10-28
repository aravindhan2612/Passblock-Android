package com.ab.an.data.datastore.serializer

import com.ab.an.data.network.dto.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class AppSettingsEntity(
    val isOnBoardingShown: Boolean = false,
    val isUserLoggedIn: Boolean = false,
    val jwtKey: String = "",
    val user: UserDto = UserDto(),
    val themeMode:String? = null
)