package com.ab.an.presentation.auth

data class AuthState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val isRegister: Boolean = false,
    val isLoading: Boolean = false,
    val authSuccess: Boolean = false,
    val usernameErrorMessage: String = "",
    val emailErrorMessage: String = "",
    val passwordErrorMessage: String = "",
    val errorMessage: String? = null
)
