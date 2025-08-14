package com.ab.an.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.an.domain.repository.AppDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val appDataStoreRepository: AppDataStoreRepository) :
    ViewModel() {

    private val _state = MutableStateFlow<Boolean>(false)
    val state = _state.asStateFlow()

    fun logout() {
        viewModelScope.launch {
            async { appDataStoreRepository.setUserLoggedIn(false) }.await()
            async { appDataStoreRepository.saveJwtToken(null) }.await()
            _state.value = true
        }

    }
}