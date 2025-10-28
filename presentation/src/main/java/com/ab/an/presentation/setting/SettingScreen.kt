package com.ab.an.presentation.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ab.an.presentation.R
import com.ab.an.presentation.theme.AppTypography
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
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        LabelIconRow(
            label = stringResource(R.string.profile),
            imageVector = Icons.AutoMirrored.Default.ArrowForward,
            onClick = navToProfile
        )
        LabelValueRow(
            label = stringResource(R.string.theme),
            value = state.themeValue,
            onClick = {
                settingViewModel.onIntent(SettingStateIntent.OnSheetChange(true))
            }
        )
        LabelValueRow(
            label = stringResource(R.string.language),
            value = "English"
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.biometrics),
                style = AppTypography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Switch(
                checked = state.checked,
                onCheckedChange = {
                    scope.launch {
                        settingViewModel.onIntent(SettingStateIntent.OnCheckedChange(it))
                    }
                },
                modifier = Modifier.scale(0.75f),
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.secondary,
                    checkedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                    uncheckedThumbColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        }
        LabelValueRow(
            label = stringResource(R.string.auto_lock),
            value = "1 minute"
        )
        LabelValueRow(
            label = stringResource(R.string.app_version),
            value = "1.2.3"
        )
        if (state.showBottomSheet) {
            ThemeBottomSheet(
                themeValue = state.themeValue,
                onRadioButtonClick = {
                    settingViewModel.onIntent(SettingStateIntent.OnThemeModeChange(it))
                },
                onDismissRequest = {
                    settingViewModel.onIntent(SettingStateIntent.OnSheetChange(false))
                },
            )
        }
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
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = AppTypography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = value,
            style = AppTypography.titleMedium,
            color = MaterialTheme.colorScheme.secondaryContainer
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
            .clickable {
                onClick()
            }
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = AppTypography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Icon(
            imageVector = imageVector,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.secondaryContainer
        )

    }
}
