package com.ab.an.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.TextUnit
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import com.ab.an.core.utils.Constants

@Composable
fun ProfilePictureAsyncImage(
    modifier : Modifier = Modifier,
    fileName: String? = null,
    label: String,
    textColor: Color = MaterialTheme.colorScheme.secondary,
    fontSize: TextUnit = TextUnit.Unspecified
) {
    val painter =rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalPlatformContext.current)
            .data("${Constants.E_BASE_URL}uploads/${fileName}")
            .build(),
    )
    val state by painter.state.collectAsState()

    when (state) {
        is AsyncImagePainter.State.Empty,
        is AsyncImagePainter.State.Loading -> {
            CircularProgressIndicator(
                modifier =  modifier
            )
        }
        is AsyncImagePainter.State.Success -> {
            if(state.painter != null) {
                Image(
                    painter = painter,
                    contentDescription = "Profile Picture",
                    modifier = modifier,
                    contentScale = ContentScale.Crop
                )
            } else {
                UserInitialsAvatar(
                    username = label,
                    modifier = modifier,
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    textColor = textColor,
                    fontSize = fontSize
                )
            }
        }
        is AsyncImagePainter.State.Error -> {
            // Show some error UI.
            UserInitialsAvatar(
                username = label,
                modifier = modifier,
                backgroundColor = MaterialTheme.colorScheme.primary,
                textColor = textColor,
                fontSize = fontSize
            )
        }
    }
}