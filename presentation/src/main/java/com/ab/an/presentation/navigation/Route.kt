package com.ab.an.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class Route(val showTopBar: Boolean): NavKey {
    @Serializable
    data object Onboarding : Route(showTopBar = false)
    @Serializable
    data class  Auth(val isRegister: Boolean = false) : Route(showTopBar = true)
}