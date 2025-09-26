package com.ab.an.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.ab.an.presentation.theme.AppTypography

@Composable
fun DetailRow(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    imageVector: ImageVector,
    contentDescription: String? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription
        )
        Spacer(modifier = Modifier.size(20.dp))
        Column {
            Text(
                text = label,
                style = AppTypography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = value,
                style = AppTypography.bodyMedium
            )
        }
    }
}