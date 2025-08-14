package com.ab.an.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.ab.an.core.R

@Composable
fun FavIconAsyncImage(
    modifier: Modifier = Modifier,
    defaultPainter : Painter = painterResource(R.drawable.outline_broken_image_24),
    iconSize: Dp = 50.dp,
    model: String? = null,
) {
    val painter = rememberAsyncImagePainter(model)
    val painterValue by painter.state.collectAsState()
        Image(
            painter = if (painterValue.painter != null) painter else defaultPainter,
            contentDescription = null,
            modifier = modifier
                .size(iconSize)
        )
}