package com.ab.an.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class RootRoute() : NavKey {
    @Serializable
    data object Splash : RootRoute()
    @Serializable
    data object Onboarding : RootRoute()
    @Serializable
    data class Auth(val isRegister: Boolean = false) : RootRoute()
    @Serializable
    data object BottomBarGraph : RootRoute()
    @Serializable
    data class AddOrEditPassword(val isEditMode: Boolean = false, val id: String? = null) : RootRoute()
    @Serializable
    data class ViewPassword(val id: String) : RootRoute()

}

