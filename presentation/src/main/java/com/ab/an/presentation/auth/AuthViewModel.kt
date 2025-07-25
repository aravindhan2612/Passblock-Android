package com.ab.an.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.an.domain.repository.AppDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val appDataStoreRepository: AppDataStoreRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    fun onEvent(event: AuthIntent) {
        when(event) {
            is AuthIntent.EmailChanged -> {}
            is AuthIntent.Auth -> {
                auth(event.title)
            }
            is AuthIntent.PasswordChanged -> {}
            is AuthIntent.UsernameChanged -> {}
        }
    }

    private fun auth(title: String) {
        _state.value = _state.value.copy(
            isLoading = true
        )
        val job = viewModelScope.launch {
            delay(5000)
            appDataStoreRepository.setUserLoggedIn(true)
        }
        job.invokeOnCompletion{
            _state.value = _state.value.copy(
                isLoading = false,
                authSuccess = true
            )
        }
    }
}