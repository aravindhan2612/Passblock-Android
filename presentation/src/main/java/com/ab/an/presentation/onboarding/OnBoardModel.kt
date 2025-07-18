package com.ab.an.presentation.onboarding

import com.ab.an.core.R
import com.ab.an.core.utils.Constants.CONTROL_YOUR_SECURITY
import com.ab.an.core.utils.Constants.EVERYTHING_IN_SINGLE_CLICK
import com.ab.an.core.utils.Constants.FAST
import com.ab.an.core.utils.Constants.FRICTIONLESS_SECURITY
import com.ab.an.core.utils.Constants.OB_DESCRIPTION_1
import com.ab.an.core.utils.Constants.OB_DESCRIPTION_2
import com.ab.an.core.utils.Constants.PASS_BLOCK
import com.ab.an.core.utils.Constants.SECURITY

data class OnBoardModel(
    val imageRes: Int,
    val headLine: String = "",
    val title: String = "",
    val description: String = ""
)

val onBoardModels = listOf(
    OnBoardModel(
        headLine = SECURITY,
        title = CONTROL_YOUR_SECURITY,
        description = OB_DESCRIPTION_1,
        imageRes = R.drawable.shield_tick
    ),
    OnBoardModel(
        headLine = FAST,
        title = EVERYTHING_IN_SINGLE_CLICK,
        description = OB_DESCRIPTION_2,
        imageRes = R.drawable.box
    ),
    OnBoardModel(
        title = PASS_BLOCK,
        description = FRICTIONLESS_SECURITY,
        imageRes = R.drawable.obthree
    )
)
