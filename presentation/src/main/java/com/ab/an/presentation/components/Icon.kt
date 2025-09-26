package com.ab.an.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ErrorIcon(
    modifier: Modifier = Modifier
) {
    Icon(
        modifier = modifier,
        imageVector = Icons.Default.Error,
        contentDescription = "Email Icon",
        tint = MaterialTheme.colorScheme.error
    )
}

@Composable
fun TopBarIcon(
    onClick: () -> Unit,
    contentDescription: String? = null,
    imageVector: ImageVector,
    tint: Color = MaterialTheme.colorScheme.primary
) {

    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size(24.dp)
        )
    }
}