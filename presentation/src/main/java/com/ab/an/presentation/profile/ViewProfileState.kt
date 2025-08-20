package com.ab.an.presentation.profile

import com.ab.an.domain.model.User

data class ViewProfileState(
    val user: User = User(),
    val isLoggedOut: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
