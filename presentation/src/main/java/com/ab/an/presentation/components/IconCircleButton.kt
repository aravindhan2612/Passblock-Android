package com.ab.an.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun IconCircleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    imageVector: ImageVector? = null,
    painter: Painter? = null,
    contentDescription: String? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
    tint: Color = MaterialTheme.colorScheme.primary,
    iconSize: Dp = 24.dp,
    containerSize: Dp = 50.dp
) {
    require(imageVector != null || painter != null) {
        "Either imageVector or painter must be provided."
    }

    Surface(
        modifier = modifier
            .size(containerSize),
        shape = CircleShape,
        color = backgroundColor,
        onClick = onClick,
        tonalElevation = 0.dp, // Flat look
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            IconCircleBackground(
                imageVector = imageVector,
                painter = painter,
                contentDescription = contentDescription,
                tint = tint,
                backgroundColor = backgroundColor,
                iconSize = iconSize,
                containerSize = containerSize
            )
        }
    }
}