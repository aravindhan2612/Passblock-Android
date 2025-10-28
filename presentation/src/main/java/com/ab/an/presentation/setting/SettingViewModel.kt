package com.ab.an.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.an.domain.repository.AppSettingsDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val preferenceDataStoreRepository: AppSettingsDataStoreRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SettingState())
    val state = _state.onStart {
        getThemeMode()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        SettingState()
    )

    private fun getThemeMode() {
        viewModelScope.launch {
            preferenceDataStoreRepository.themeMode.collect { theme ->
                _state.value = _state.value.copy(
                    themeValue = theme ?: ThemeMode.SYSTEM.value
                )
            }
        }
    }

    fun onIntent(intent: SettingStateIntent) {
        when (intent) {
            is SettingStateIntent.OnCheckedChange -> {
                _state.value = _state.value.copy(
                    checked = intent.checked
                )
            }

            is SettingStateIntent.OnSheetChange -> {
                _state.value = _state.value.copy(
                    showBottomSheet = intent.isSheetOpen
                )
            }

            is SettingStateIntent.OnThemeModeChange -> {
                viewModelScope.launch {
                    preferenceDataStoreRepository.saveThemeMode(intent.themeValue)
                }
            }
        }
    }

}