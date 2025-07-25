package com.ab.an.presentation.auth

sealed class AuthIntent {
    data class  UsernameChanged(val username: String) : AuthIntent()
    data class EmailChanged(val email: String) : AuthIntent()
    data class PasswordChanged(val password: String) : AuthIntent()
    data class Auth(val title: String) : AuthIntent()
    data object TabSwitched : AuthIntent()
}