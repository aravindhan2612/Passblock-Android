package com.ab.an.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.an.core.utils.Resource
import com.ab.an.domain.repository.AppSettingsDataStoreRepository
import com.ab.an.domain.repository.PasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val passwordRepository: PasswordRepository,
    private val appSettingsDataStoreRepository: AppSettingsDataStoreRepository
) : ViewModel() {


    private val _state = MutableStateFlow(HomeState())
    val state = _state.onStart {
        getPasswords()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        HomeState()
    )

    fun fetchPasswords() {
        viewModelScope.launch {
            passwordRepository.fetchPasswords().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true,
                            errorMessage = null
                        )
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
            }
        }
    }

    private fun getPasswords() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                errorMessage = null
            )
            val user = appSettingsDataStoreRepository.getUser().first()
            passwordRepository.getPasswords(user.id).collect { passwordEntities ->
                if (passwordEntities.isEmpty()) {
                    fetchPasswords()
                } else {
                    val sectionListItems = mutableListOf<PasswordSectionListItem>()
                    passwordEntities.groupBy { passwordEntity ->
                        passwordEntity.tag
                    }.forEach { (tag, passwords) ->
                        sectionListItems.add(PasswordSectionListItem.Header(tag))
                        passwords.forEach { password ->
                            sectionListItems.add(PasswordSectionListItem.Item(password))
                        }
                    }
                    _state.value = _state.value.copy(
                        isLoading = false,
                        passwords = sectionListItems
                    )
                }
            }
        }
    }
}