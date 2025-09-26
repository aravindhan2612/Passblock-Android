package com.ab.an.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
fun EditOptionRow(
    leadingIcon: ImageVector,
    leadingIconContentDescription: String? = null,
    label: String,
    value: String? = null,
    trailingIcon: ImageVector,
    trailingIconContentDescription: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = leadingIconContentDescription
            )
            Spacer(modifier = Modifier.size(20.dp))
            Column {
                Text(
                    text = label,
                    style = AppTypography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                value?.let {
                    Text(
                        text = it,
                        style = AppTypography.bodyMedium
                    )
                }
            }
        }
        Icon(
            imageVector = trailingIcon,
            contentDescription = trailingIconContentDescription,
            modifier = Modifier.size(20.dp)
        )
    }
}