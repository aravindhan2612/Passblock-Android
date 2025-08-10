package com.ab.an.presentation.password

sealed class AddOrEditPasswordIntent {
    data class NameChanged(val name: String) : AddOrEditPasswordIntent()
    data class UserNameChanged(val userName: String) : AddOrEditPasswordIntent()
    data class LinkChanged(val link: String) : AddOrEditPasswordIntent()
    data class PasswordChanged(val password: String) : AddOrEditPasswordIntent()
    data class TagChanged(val tag: String) : AddOrEditPasswordIntent()

    object Submit : AddOrEditPasswordIntent()
}