package com.ab.an.presentation.updateContactInfo

import com.ab.an.domain.model.User

data class UpdateContactInfoState(
    val user: User = User(),
    val fullNameError: String = "",
    val emailError: String = "",
    val phoneNumberError: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val success: Boolean = false,
)
