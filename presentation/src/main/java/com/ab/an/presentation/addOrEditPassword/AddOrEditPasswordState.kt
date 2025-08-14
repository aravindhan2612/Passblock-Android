package com.ab.an.presentation.addOrEditPassword

import com.ab.an.domain.model.Password

data class AddOrEditPasswordState(
    val passwordEntity: Password = Password(),
    val nameError: String = "",
    val userNameError: String = "",
    val linkError: String = "",
    val passwordError: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val fetchPasswordError: String? = null
)

val tags = listOf(
"Work",
"Personal",
"Entertainment",
"Shopping",
"Education",
"Health",
"Finance",
"Productivity",
"Lifestyle",
"Priority",
"Other"
)
