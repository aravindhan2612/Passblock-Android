package com.ab.an.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit

@Composable
fun UserInitialsAvatar(
    username: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.secondary,
    fontSize: TextUnit = TextUnit.Unspecified
) {
    val initials = remember(username) {
        val names = username.split(" ")
        val firstInitial = names.firstOrNull()?.firstOrNull()?.uppercaseChar() ?: "?"
        val lastInitial = if (names.size > 1) {
            names.last().firstOrNull()?.uppercaseChar() ?: ' '
        } else {
             ""
        }
        "$firstInitial$lastInitial"
    }
    Box(
        modifier = modifier
            .background(backgroundColor, CircleShape)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            style = MaterialTheme.typography.headlineSmall,
            color = textColor,
            textAlign = TextAlign.Center,
            fontSize = fontSize
        )
    }
}