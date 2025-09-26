package com.ab.an.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.ab.an.presentation.theme.AppTypography

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
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        enabled = enabled
    ) {
        Text(
            text = label,
            style = AppTypography.bodyLarge,
            modifier = Modifier.padding(5.dp),
        )
    }
}


@Composable
fun PrimaryOutlinedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: String,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.outlineVariant)
    ) {
        Text(
            text = label,
            style = AppTypography.bodyLarge,
            modifier = Modifier.padding(5.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun LeadingIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    label: String,
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            icon()
            Text(
                text = label,
                style = AppTypography.bodyLarge,
            )
        }
    }
}

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: String,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        enabled = enabled
    ) {
        Text(
            text = label,
            style = AppTypography.bodyLarge,
            modifier = Modifier.padding(5.dp),
        )
    }
}

@Composable
fun SecondaryOutlinedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: String,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Text(
            text = label,
            style = AppTypography.bodyLarge,
            modifier = Modifier.padding(5.dp),
            color = MaterialTheme.colorScheme.secondary
        )
    }
}