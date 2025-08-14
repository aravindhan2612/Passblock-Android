package com.ab.an.presentation.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ab.an.core.R
import com.ab.an.presentation.navigation.RootRoute
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun SplashScreen(
    onComplete: (RootRoute) -> Unit,
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    val state by splashViewModel.state.collectAsStateWithLifecycle()
    val preLoaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.loader
        )
    )

    val preLoaderProgress by animateLottieCompositionAsState(
        preLoaderLottieComposition,
        isPlaying = true,
        speed = 1f
    )

    LaunchedEffect(state?.route, preLoaderProgress) {
        if (state?.route != null) {
            state?.route?.let { route -> onComplete(route) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = preLoaderLottieComposition,
            progress = preLoaderProgress,
            modifier = Modifier.size(200.dp),
        )
        Text(
            text = "Loading...",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
        )
    }
}