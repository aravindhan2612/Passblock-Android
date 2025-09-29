package com.ab.an.presentation.addOrEditPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.an.core.utils.Resource
import com.ab.an.domain.model.Password
import com.ab.an.domain.repository.PasswordRepository
import com.ab.an.domain.usecase.validate.ValidateLinkUseCase
import com.ab.an.domain.usecase.validate.ValidateNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddOrEditPasswordViewModel @Inject constructor(
    private val passwordRepository: PasswordRepository,
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateLinkUseCase: ValidateLinkUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(
        AddOrEditPasswordState(
            passwordEntity = Password(
                tag = categories.last().name
            )
        )
    )
    val state = _state.asStateFlow()

    fun onIntent(intent: AddOrEditPasswordIntent) {
        when (intent) {
            is AddOrEditPasswordIntent.LinkChanged -> {
                _state.value = _state.value.copy(
                    passwordEntity = _state.value.passwordEntity.copy(
                        link = intent.link,
                    ),
                    linkError = ""
                )
            }

            is AddOrEditPasswordIntent.NameChanged -> {
                _state.value = _state.value.copy(
                    passwordEntity = _state.value.passwordEntity.copy(
                        name = intent.name
                    ),
                    nameError = ""
                )
            }

            is AddOrEditPasswordIntent.PasswordChanged -> {
                _state.value = _state.value.copy(
                    passwordEntity = _state.value.passwordEntity.copy(
                        password = intent.password
                    ),
                    passwordError = ""
                )
            }

            is AddOrEditPasswordIntent.Submit -> {
                submit(intent.isEdit)
            }

            is AddOrEditPasswordIntent.UserNameChanged -> {
                _state.value = _state.value.copy(
                    passwordEntity = _state.value.passwordEntity.copy(
                        username = intent.userName
                    ),
                    userNameError = ""
                )
            }

            is AddOrEditPasswordIntent.TagChanged -> {
                _state.value = _state.value.copy(
                    passwordEntity = _state.value.passwordEntity.copy(
                        tag = intent.tag.name
                    )
                )
            }

            is AddOrEditPasswordIntent.FetchPassword -> fetchPassword(intent.id)
        }
    }

    private fun submit(isEditMode: Boolean) {
        val nameError = validateNameUseCase(_state.value.passwordEntity.name, "Name")
        val userNameError = validateNameUseCase(_state.value.passwordEntity.username, "User Name")
        val linkError = validateLinkUseCase(_state.value.passwordEntity.link)
        val passwordError = validateNameUseCase(_state.value.passwordEntity.password, "Password")
        val hasError = listOf(
            nameError,
            userNameError,
            linkError,
            passwordError,
        ).any { it.isNotEmpty() }
        if (hasError) {
            _state.value = _state.value.copy(
                nameError = nameError,
                userNameError = userNameError,
                linkError = linkError,
                passwordError = passwordError,
            )
            return
        }
        viewModelScope.launch {
            passwordRepository.addOrUpdatePassword(isEdit = isEditMode, _state.value.passwordEntity).collect { result ->
                when (result) {
                    is Resource.Error<Password> -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }

                    is Resource.Loading<Password> -> {
                        _state.value = _state.value.copy(
                            isLoading = true,
                            errorMessage = null,
                            success = false
                        )
                    }

                    is Resource.Success<Password> -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            success = true
                        )
                    }
                }
            }
        }
    }

    private fun fetchPassword(id: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                fetchPasswordError = null
            )
            passwordRepository.getPassword(id).collect { password ->
                password?.let {
                    _state.value = _state.value.copy(
                        passwordEntity = it,
                        isLoading = false,
                        fetchPasswordError = null
                    )
                }
            }
        }
    }
}