package com.ab.an.presentation.updateContactInfo

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.an.core.utils.CommonUtils.getLocaleRegion
import com.ab.an.core.utils.CommonUtils.getNetworkRegion
import com.ab.an.core.utils.CommonUtils.getSimRegion
import com.ab.an.core.utils.Resource
import com.ab.an.domain.repository.AppSettingsDataStoreRepository
import com.ab.an.domain.usecase.user.UpdateUserUseCase
import com.ab.an.domain.usecase.validate.ValidateEmailUseCase
import com.ab.an.domain.usecase.validate.ValidatePhoneNumberUseCase
import com.ab.an.domain.usecase.validate.ValidateUsernameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateContactInfoViewModel @Inject constructor(
    private val appSettingsDataStoreRepository: AppSettingsDataStoreRepository,
    private val updateUserUseCase: UpdateUserUseCase,
    private val validatePhoneNumberUseCase: ValidatePhoneNumberUseCase,
    private val validateUsernameUseCase: ValidateUsernameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UpdateContactInfoState())
    val state = _state.onStart {
        fetchUser()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        UpdateContactInfoState()
    )


    private fun fetchUser() {
        viewModelScope.launch {
            appSettingsDataStoreRepository.getUser().collect { user ->
                _state.value = _state.value.copy(
                    user = user
                )
            }
        }
    }

    fun onIntent(intent: UpdateContactInfoIntent) {
        when (intent) {
            is UpdateContactInfoIntent.FullNameChange -> {
                _state.value =
                    state.value.copy(user = state.value.user.copy(fullName = intent.fullName ), fullNameError = "")
            }

            is UpdateContactInfoIntent.DateChange -> {
                _state.value = state.value.copy(user = state.value.user.copy(dob = intent.date))
            }

            is UpdateContactInfoIntent.EmailChange -> {
                _state.value = state.value.copy(user = state.value.user.copy(email = intent.email), emailError = "")
            }

            is UpdateContactInfoIntent.PhoneNumberChange -> {
                _state.value =
                    state.value.copy(user = state.value.user.copy(phoneNumber = intent.phoneNumber), phoneNumberError = "")
            }

            is UpdateContactInfoIntent.Submit -> {
                submit(intent.context)
            }
        }
    }

    private fun submit(context: Context) {
        val region = getSimRegion(context) ?: getNetworkRegion(context) ?: getLocaleRegion()
        val userNameError = validateUsernameUseCase(_state.value.user.fullName)
        val emailError = validateEmailUseCase(_state.value.user.email)
        val phoneNumberError = validatePhoneNumberUseCase(
            _state.value.user.phoneNumber,
            region
        )
        val hasError = listOf(
            userNameError,
            emailError,
            phoneNumberError
        ).any { it.isNotBlank() }
        if (hasError) {
            _state.value = state.value.copy(
                fullNameError = userNameError,
                emailError = emailError,
                phoneNumberError = phoneNumberError
            )
            return
        }
        viewModelScope.launch {
            updateUserUseCase.invoke(_state.value.user).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _state.value = state.value.copy(isLoading = false, success = true)
                    }
                    is Resource.Error -> {
                        _state.value =
                            state.value.copy(isLoading = false, errorMessage = result.message)
                    }
                }
            }
        }
    }
}