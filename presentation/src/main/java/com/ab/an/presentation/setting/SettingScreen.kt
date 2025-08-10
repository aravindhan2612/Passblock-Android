package com.ab.an.presentation.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ab.an.presentation.components.PrimaryButton
import com.ab.an.presentation.components.PrimaryText


@Composable
fun SettingScreen(innerPadding: PaddingValues, navToAuth: () -> Unit, viewModel: SettingViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(state) {
        if(state) {
            navToAuth()
        }
    }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        PrimaryText(
            text = "Setting screen"
        )
        PrimaryButton(
            onClick = {
                viewModel.logout()
            },
            label = "Log out"
        )
    }
}