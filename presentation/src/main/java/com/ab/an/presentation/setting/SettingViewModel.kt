package com.ab.an.presentation.setting

import androidx.lifecycle.ViewModel
import com.ab.an.domain.repository.AppSettingsDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val preferenceDataStoreRepository: AppSettingsDataStoreRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SettingState())
    val state = _state.asStateFlow()

    fun onIntent(intent: SettingStateIntent) {
        when(intent) {
            is SettingStateIntent.OnCheckedChange -> {
                _state.value = _state.value.copy(
                    checked = intent.checked
                )
            }
        }
    }

}