package com.ab.an.domain.model

data class AppSettings(
    val isOnBoardingShown: Boolean = false,
    val isUserLoggedIn: Boolean = false,
    val jwtKey: String = "",
    val user: User = User(),
    val themeMode:String? = null
)