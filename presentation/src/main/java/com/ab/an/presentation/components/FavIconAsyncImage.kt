package com.ab.an.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.ab.an.core.R

@Composable
fun FavIconAsyncImage(
    modifier: Modifier = Modifier,
    defaultPainter: Painter = painterResource(R.drawable.outline_broken_image_24),
    iconSize: Dp = 50.dp,
    model: String? = null,
    defaultPainterColor: Color = MaterialTheme.colorScheme.primary
) {

    SubcomposeAsyncImage(
        model = model,
        contentDescription = "",
        modifier = modifier.size(iconSize),
    ) {
        val state by painter.state.collectAsState()
        when (state) {
            is AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator()
            }

            is AsyncImagePainter.State.Success -> {
                SubcomposeAsyncImageContent()
            }
            else -> {
                Image(
                    painter = defaultPainter,
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(color = defaultPainterColor)
                )
            }
        }
    }
}