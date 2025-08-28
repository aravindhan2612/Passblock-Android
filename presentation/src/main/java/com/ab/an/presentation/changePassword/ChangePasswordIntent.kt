package com.ab.an.presentation.changePassword

sealed class ChangePasswordIntent {
    data class CurrentPasswordChanged(val currentPassword: String) : ChangePasswordIntent()
    data class NewPasswordChanged(val newPassword: String) : ChangePasswordIntent()
    data class ConfirmNewPasswordChanged(val confirmNewPassword: String) : ChangePasswordIntent()
    object Submit : ChangePasswordIntent()
    object OnDismiss : ChangePasswordIntent()
}