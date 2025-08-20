package com.ab.an.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.an.core.utils.Resource
import com.ab.an.domain.repository.AppDataStoreRepository
import com.ab.an.domain.usecase.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewProfileViewModel @Inject constructor(
    private val appDataStoreRepository: AppDataStoreRepository,
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
            getCurrentUserUseCase().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        result.data?.let {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                user = it
                            )
                        }
                    }
                }
            }
        }
    }

     fun logout() {
        viewModelScope.launch {
            async { appDataStoreRepository.setUserLoggedIn(false) }.await()
            async { appDataStoreRepository.saveJwtToken(null) }.await()
            _state.value = _state.value.copy(
                isLoggedOut = true
            )
        }

    }
}