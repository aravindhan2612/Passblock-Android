package com.ab.an.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: String,
    labelFontSize: TextUnit = TextUnit.Unspecified,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.secondary
        ),
        enabled = enabled
    ) {
        Text(
            text = label,
            fontSize = labelFontSize,
            modifier = Modifier.padding(5.dp),
        )
    }
}


@Composable
fun PrimaryOutlinedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: String,
    labelFontSize: TextUnit = TextUnit.Unspecified,
    isLoading: Boolean = false
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = label,
            fontSize = labelFontSize,
            modifier = Modifier.padding(5.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}