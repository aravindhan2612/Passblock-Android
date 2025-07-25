package com.ab.an.presentation.password

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AddOrEditPasswordScreen( navToHome: () -> Unit) {
    Column(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Text(text = "Add Or Edit Password")
    }
}