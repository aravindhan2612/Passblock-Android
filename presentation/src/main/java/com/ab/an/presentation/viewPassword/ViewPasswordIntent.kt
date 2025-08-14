package com.ab.an.presentation.viewPassword

sealed class ViewPasswordIntent {
    data class FetchPassword(val id: String) : ViewPasswordIntent()
    object OpenDeleteDialog : ViewPasswordIntent()
    object CopyPassword : ViewPasswordIntent()
    object ChangePassword : ViewPasswordIntent()
    object DeletePassword : ViewPasswordIntent()
    object CloseDeleteDialog: ViewPasswordIntent()
}