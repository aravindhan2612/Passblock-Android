package com.ab.an.presentation.home

import com.ab.an.domain.model.Password
import com.ab.an.presentation.addOrEditPassword.Category

data class HomeState(
    val passwords: List<Password> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchText: String = "",
    val filteredPasswords: List<Password> = emptyList(),
    val selectedCategory: Category? = null
)
