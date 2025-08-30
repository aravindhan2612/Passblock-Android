package com.ab.an.presentation.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.an.core.utils.Resource
import com.ab.an.domain.model.User
import com.ab.an.domain.usecase.user.UserLoginUseCase
import com.ab.an.domain.usecase.user.UserRegisterUseCase
import com.ab.an.domain.usecase.validate.ValidateEmailUseCase
import com.ab.an.domain.usecase.validate.ValidatePasswordUseCase
import com.ab.an.domain.usecase.validate.ValidateUsernameUseCase
import com.ab.an.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val loginUseCase: UserLoginUseCase,
    private val registerUseCase: UserRegisterUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateUsernameUseCase: ValidateUsernameUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState(
        user = User().copy(
            email = "Test@gmail.com",
            password = "Test@123"
        )
    ))
    val state = _state.asStateFlow()

    fun onIntent(event: AuthIntent) {
        when (event) {
            is AuthIntent.EmailChanged -> {
                _state.value = _state.value.copy(
                    user = _state.value.user.copy(
                        email = event.email,
                    ),
                    emailErrorMessage = ""
                )
            }

            is AuthIntent.Auth -> {
                auth(event.title)
            }

            is AuthIntent.PasswordChanged -> {
                _state.value = _state.value.copy(
                    user = _state.value.user.copy(
                        password = event.password,
                    ),
                    passwordErrorMessage = ""
                )
            }

            is AuthIntent.UsernameChanged -> {
                _state.value = _state.value.copy(
                    user = _state.value.user.copy(
                        fullName = event.username,
                    ),
                    usernameErrorMessage = ""
                )
            }

            AuthIntent.TabSwitched -> {
                _state.value = AuthState()
            }
        }
    }

    private fun auth(title: String) {
        if (title == context.getString(R.string.register)) {
            register()
        } else {
            login()
        }
    }

    private fun login() {
        val emailResult = validateEmailUseCase(_state.value.user.email)
        val passwordResult = validatePasswordUseCase(_state.value.user.password)
        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { it.isNotBlank() }
        if (hasError) {
            _state.value = _state.value.copy(
                emailErrorMessage = emailResult,
                passwordErrorMessage = passwordResult
            )
            return
        }
        viewModelScope.launch {
            loginUseCase(_state.value.user).onEach { result ->
                when (result) {
                    is Resource.Error<User> -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }

                    is Resource.Loading<User> -> {
                        _state.value = _state.value.copy(
                            isLoading = true,
                            errorMessage = null,
                            authSuccess = false
                        )
                    }

                    is Resource.Success<User> -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            authSuccess = true
                        )
                    }
                }
            }.stateIn(viewModelScope)
        }
    }

    private fun register() {
        val usernameResult = validateUsernameUseCase(_state.value.user.fullName)
        val emailResult = validateEmailUseCase(_state.value.user.email)
        val passwordResult = validatePasswordUseCase(_state.value.user.password)
        val hasError = listOf(
            usernameResult,
            emailResult,
            passwordResult
        ).any { it.isNotBlank() }
        if (hasError) {
            _state.value = _state.value.copy(
                usernameErrorMessage = usernameResult,
                emailErrorMessage = emailResult,
                passwordErrorMessage = passwordResult
            )
            return
        }
        viewModelScope.launch {
            registerUseCase(_state.value.user).onEach { result ->
                when (result) {
                    is Resource.Error<User> -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )

                    }

                    is Resource.Loading<User> -> {
                        _state.value = _state.value.copy(
                            isLoading = true,
                            errorMessage = null,
                            authSuccess = false
                        )
                    }

                    is Resource.Success<User> -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            authSuccess = true
                        )
                    }
                }
            }.stateIn(viewModelScope)
        }
    }
}