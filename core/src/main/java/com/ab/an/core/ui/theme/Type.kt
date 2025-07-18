package com.ab.an.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.ab.an.core.R

// Set of Material typography styles to start with

val poppinsFont = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
)

@Composable
fun appTypography(): Typography {
    return with(MaterialTheme.typography) {
        copy(
            displayLarge = displayLarge.copy(
                fontFamily = poppinsFont,
                fontWeight = FontWeight.SemiBold
            ),
            displayMedium = displayMedium.copy(
                fontFamily = poppinsFont,
                fontWeight = FontWeight.SemiBold
            ),
            displaySmall = displaySmall.copy(
                fontFamily = poppinsFont,
                fontWeight = FontWeight.SemiBold
            ),
            headlineLarge = headlineLarge.copy(
                fontFamily = poppinsFont,
                fontWeight = FontWeight.SemiBold
            ),
            headlineMedium = headlineMedium.copy(
                fontFamily = poppinsFont,
                fontWeight = FontWeight.SemiBold
            ),
            headlineSmall = headlineSmall.copy(
                fontFamily = poppinsFont,
                fontWeight = FontWeight.SemiBold
            ),
            titleLarge = titleLarge.copy(
                fontFamily = poppinsFont,
                fontWeight = FontWeight.SemiBold
            ),
            titleMedium = titleMedium.copy(
                fontFamily = poppinsFont,
                fontWeight = FontWeight.SemiBold
            ),
            titleSmall = titleSmall.copy(
                fontFamily = poppinsFont,
                fontWeight = FontWeight.SemiBold
            ),
            bodyLarge = bodyLarge.copy(fontFamily = poppinsFont, fontWeight = FontWeight.Normal),
            bodyMedium = bodyMedium.copy(fontFamily = poppinsFont, fontWeight = FontWeight.Normal),
            bodySmall = bodySmall.copy(fontFamily = poppinsFont, fontWeight = FontWeight.Normal),
            labelLarge = labelLarge.copy(fontFamily = poppinsFont, fontWeight = FontWeight.Normal),
            labelMedium = labelMedium.copy(
                fontFamily = poppinsFont,
                fontWeight = FontWeight.Normal
            ),
            labelSmall = labelSmall.copy(fontFamily = poppinsFont, fontWeight = FontWeight.Normal),
        )
    }
}