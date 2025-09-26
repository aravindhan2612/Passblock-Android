package com.ab.an.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun FilterChip(
    onClick: (Boolean) -> Unit,
    text: String,
    selected: Boolean,
    imageVector: ImageVector? = null,
) {

    FilterChip(
        onClick ={
            onClick(!selected)
        },
        label = {
            Text(text = text)
        },
        selected = selected,
        leadingIcon = {
            imageVector?.let {
                Icon(
                    imageVector = it,
                    contentDescription = "$text icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.secondary
        )
    )
}