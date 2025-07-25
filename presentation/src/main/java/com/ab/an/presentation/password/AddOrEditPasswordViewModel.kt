package com.ab.an.presentation.password

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
class AddOrEditPasswordViewModel @Inject constructor(
    private val appDataStoreRepository: AppDataStoreRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AddOrEditPasswordState>(AddOrEditPasswordState())
    val state = _state.asStateFlow()

    fun onIntent(intent: AddOrEditPasswordIntent) {
        when (intent) {
            is AddOrEditPasswordIntent.LinkChanged -> {
                _state.value = _state.value.copy(
                    passwordEntity = _state.value.passwordEntity.copy(
                        link = intent.link
                    )
                )
            }

            is AddOrEditPasswordIntent.NameChanged -> {
                _state.value = _state.value.copy(
                    passwordEntity = _state.value.passwordEntity.copy(
                        name = intent.name
                    )
                )
            }

            is AddOrEditPasswordIntent.PasswordChanged -> {
                _state.value = _state.value.copy(
                    passwordEntity = _state.value.passwordEntity.copy(
                        password = intent.password
                    )
                )
            }

            AddOrEditPasswordIntent.Submit -> {
                submit()
            }

            is AddOrEditPasswordIntent.UserNameChanged -> {
                _state.value = _state.value.copy(
                    passwordEntity = _state.value.passwordEntity.copy(
                        userName = intent.userName
                    )
                )
            }
        }
    }

    private fun submit() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            )
            delay(5000)
            _state.value = _state.value.copy(
                isLoading = false,
                success = true
            )
        }
    }
}