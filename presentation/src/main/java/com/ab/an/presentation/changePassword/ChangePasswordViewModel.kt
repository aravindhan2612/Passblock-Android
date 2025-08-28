package com.ab.an.presentation.changePassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.an.core.utils.Resource
import com.ab.an.domain.usecase.UpdateUserPasswordUseCase
import com.ab.an.domain.usecase.ValidateNameUseCase
import com.ab.an.domain.usecase.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val validateNameUseCase: ValidateNameUseCase,
    private val userPasswordUseCase: UpdateUserPasswordUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ChangePasswordState())
    val state = _state.asStateFlow()

    fun onIntent(changePasswordIntent: ChangePasswordIntent) {
        when (changePasswordIntent) {
            is ChangePasswordIntent.ConfirmNewPasswordChanged -> {
                _state.value = _state.value.copy(
                    confirmNewPassword = changePasswordIntent.confirmNewPassword,
                    confirmNewPasswordError = validateConfirmPassword(changePasswordIntent.confirmNewPassword)
                )
            }

            is ChangePasswordIntent.CurrentPasswordChanged -> {
                _state.value = _state.value.copy(
                    currentPassword = changePasswordIntent.currentPassword
                )
            }

            is ChangePasswordIntent.NewPasswordChanged -> {
                _state.value = _state.value.copy(
                    newPassword = changePasswordIntent.newPassword
                )
            }

            ChangePasswordIntent.Submit -> {
                submit()
            }

            ChangePasswordIntent.OnDismiss -> {
                _state.value = _state.value.copy(
                    errorMessage = null
                )
            }
        }
    }

    private fun validateConfirmPassword(confirmNewPassword: String): String {
        return if (confirmNewPassword.isNotBlank() && _state.value.newPassword != confirmNewPassword) {
            "Passwords do not match"
        } else {
            ""
        }
    }

    private fun submit() {
        val currentPasswordError = validateNameUseCase(_state.value.currentPassword, "Password")
        val newPasswordError = validatePasswordUseCase(_state.value.newPassword)
        val hasError = listOf(
            currentPasswordError,
            newPasswordError,
        ).any { it.isNotEmpty() }
        if (hasError) {
            _state.value = _state.value.copy(
                currentPasswordError = currentPasswordError,
                newPasswordError = newPasswordError,
            )
            return
        }
        viewModelScope.launch {
            userPasswordUseCase.invoke(_state.value.currentPassword, _state.value.newPassword)
                .collect { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                errorMessage = resource.message,
                                success = false,
                                isLoading = false
                            )
                        }

                        is Resource.Loading -> {
                            _state.value = _state.value.copy(
                                isLoading = true,
                                success = false,
                                errorMessage = null
                            )
                        }

                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                success = true,
                                errorMessage = null
                            )
                        }
                    }
                }
        }
    }
}