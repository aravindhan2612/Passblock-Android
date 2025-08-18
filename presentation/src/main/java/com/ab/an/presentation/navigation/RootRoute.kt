package com.ab.an.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class RootRoute()  {
    @Serializable
    object Splash : RootRoute()
    @Serializable
    object Onboarding : RootRoute()
    @Serializable
    data class Auth(val isRegister: Boolean = false) : RootRoute()
    @Serializable
    object BottomBarGraph : RootRoute()
    @Serializable
    data class AddOrEditPassword(val isEditMode: Boolean = false, val id: String? = null) : RootRoute()
    @Serializable
    data class ViewPassword(val id: String) : RootRoute()
    @Serializable
    object Profile: RootRoute()

}

