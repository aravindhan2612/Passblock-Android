package com.ab.an.presentation.home

data class HomeState(
    val passwords: List<SectionListItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
