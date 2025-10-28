package com.ab.an.passblock

import com.ab.an.presentation.navigation.RootRoute

data class MainState(
    val route: RootRoute? = null,
    val isLoading: Boolean = true,
    val theme: String? = null
)