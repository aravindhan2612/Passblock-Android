package com.ab.an.presentation.onboarding


data class OnBoardState(
    val isComplete: Boolean = false,
    val isRegister: Boolean = false
)
data class OnBoardDetail(
    val imageRes: Int,
    val headLine: String = "",
    val title: String = "",
    val description: String = ""
)


