package com.ab.an.presentation.viewPassword

import com.ab.an.domain.model.Password

data class ViewPasswordState(
    val password: Password = Password(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val showDeleteDialog: Boolean = false,
    val isPasswordDeleteSuccess: Boolean = false,
    val deleteError: String? = null,
)
