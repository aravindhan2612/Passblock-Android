package com.ab.an.presentation.changePassword

data class ChangePasswordState(
    val currentPassword:String = "",
    val newPassword:String = "",
    val confirmNewPassword: String = "",
    val currentPasswordError: String = "",
    val newPasswordError: String = "",
    val confirmNewPasswordError: String = "",
    val success: Boolean = false,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
