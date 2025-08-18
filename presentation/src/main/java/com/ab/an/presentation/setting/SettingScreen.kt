package com.ab.an.presentation.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ab.an.presentation.components.PrimaryText
import kotlinx.coroutines.launch


@Composable
fun SettingScreen(
    innerPadding: PaddingValues,
    navToProfile: () -> Unit,
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    val state by settingViewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.secondary
            )
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState())
        ,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        LabelIconRow(
            label = "Profile",
            imageVector = Icons.AutoMirrored.Default.ArrowForward,
            onClick = navToProfile
        )
        LabelValueRow(
            label = "Theme",
            value = "Light"
        )
        LabelValueRow(
            label = "Language",
            value = "English"
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PrimaryText(
                text = "Biometrics"
            )
            Switch(
                checked = state.checked,
                onCheckedChange = {
                    scope.launch {
                        settingViewModel.onIntent(SettingStateIntent.OnCheckedChange(it))
                    }
                },
                modifier = Modifier.scale(0.85f)
            )
        }
        LabelValueRow(
            label = "Auto-lock",
            value = "1 minute"
        )
        LabelValueRow(
            label = "Password Strength",
            value = "Strong"
        )
        LabelValueRow(
            label = "App Version",
            value = "1.2.3"
        )
        LabelIconRow(
            label = "Terms of Service",
            imageVector = Icons.AutoMirrored.Default.ArrowForward
        )
        LabelIconRow(
            label = "Privacy Policy",
            imageVector = Icons.AutoMirrored.Default.ArrowForward
        )

    }
}

@Composable
fun LabelValueRow(
    label: String,
    value: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PrimaryText(
            text = label
        )
        PrimaryText(
            text = value
        )
    }
}

@Composable
fun LabelIconRow(
    label: String,
    imageVector: ImageVector,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{
                onClick()
            }
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PrimaryText(
            text = label
        )
        Icon(
            imageVector = imageVector,
            contentDescription = "",
            modifier = Modifier.size(20.dp)
        )

    }
}