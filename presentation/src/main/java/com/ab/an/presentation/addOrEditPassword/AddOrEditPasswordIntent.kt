package com.ab.an.presentation.addOrEditPassword

sealed class AddOrEditPasswordIntent {
    data class NameChanged(val name: String) : AddOrEditPasswordIntent()
    data class UserNameChanged(val userName: String) : AddOrEditPasswordIntent()
    data class LinkChanged(val link: String) : AddOrEditPasswordIntent()
    data class PasswordChanged(val password: String) : AddOrEditPasswordIntent()
    data class TagChanged(val tag: Category) : AddOrEditPasswordIntent()
    data class Submit(val isEdit: Boolean) : AddOrEditPasswordIntent()
    data class  FetchPassword(val id: String): AddOrEditPasswordIntent()
}