package com.ab.an.presentation.auth

import com.ab.an.domain.model.User

data class AuthState(
    val user: User = User(),
    val isLoading: Boolean = false,
    val authSuccess: Boolean = false,
    val usernameErrorMessage: String = "",
    val emailErrorMessage: String = "",
    val passwordErrorMessage: String = "",
    val errorMessage: String? = null
)
