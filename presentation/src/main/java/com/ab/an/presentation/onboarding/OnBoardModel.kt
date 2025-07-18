package com.ab.an.presentation.onboarding

import com.ab.an.core.R

data class OnBoardModel(
    val imageRes: Int,
    val headLine: String = "",
    val title: String = "",
    val description: String = ""
)

val onBoardModels = listOf(
    OnBoardModel(
        headLine = "Security",
        title = "Control your security",
        description = "This application is build on blockchain so that you can get 100% security across websites & applications with single app.",
        imageRes = R.drawable.shield_tick
    ),
    OnBoardModel(
        headLine = "Fast",
        title = "Everything in single click",
        description = "Add, generate, store, transfer, sync, export & copy all your passwords in single click. Use autofill for quick action without opening app.",
        imageRes = R.drawable.box
    ),
    OnBoardModel(
        title = "Passblock",
        description = "Frictionless Security",
        imageRes = R.drawable.obthree
    )
)
