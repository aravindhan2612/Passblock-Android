package com.ab.an.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.an.core.utils.Resource
import com.ab.an.domain.model.User
import com.ab.an.domain.repository.AppSettingsDataStoreRepository
import com.ab.an.domain.usecase.user.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewProfileViewModel @Inject constructor(
    private val appSettingsDataStoreRepository: AppSettingsDataStoreRepository,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ViewProfileState())
    val state = _state.onStart {
        fetchUser()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ViewProfileState()
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

    fun fetchUserFromApi() {
        viewModelScope.launch {
            getCurrentUserUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            user = result.data ?: User(),
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true,
                            error = null
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            appSettingsDataStoreRepository.logout().collect {
                _state.value = _state.value.copy(
                    isLoggedOut = true
                )
            }
        }

    }
}