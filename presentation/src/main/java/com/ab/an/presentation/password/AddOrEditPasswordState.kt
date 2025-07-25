package com.ab.an.presentation.password

import com.ab.an.domain.model.PasswordEntity

data class AddOrEditPasswordState(
    val passwordEntity: PasswordEntity = PasswordEntity(),
    val nameError: String = "",
    val userNameError: String = "",
    val linkError: String = "",
    val passwordError: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val success: Boolean = false,
)
