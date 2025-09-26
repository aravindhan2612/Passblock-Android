package com.ab.an.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.an.core.utils.Resource
import com.ab.an.domain.repository.AppSettingsDataStoreRepository
import com.ab.an.domain.repository.PasswordRepository
import com.ab.an.presentation.addOrEditPassword.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
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

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.FetchPasswords -> {
                fetchPasswords()
            }

            is HomeIntent.OnSearchTextChanged -> {
                filterPasswordsBySearchText(text = intent.text)
            }

            is HomeIntent.OnCategoryChanged -> {
                filterPasswordsByCategory(intent.selected, intent.category)
            }
        }
    }

    private fun filterPasswordsBySearchText(text: String = "") {
        _state.update { currentState ->
            val newFilteredItems = if (text.isBlank()) {
                currentState.passwords
            } else {
                currentState.passwords.filter {
                    it.name.contains(text, ignoreCase = true) || it.username.contains(
                        text,
                        ignoreCase = true
                    )
                }
            }
            currentState.copy(
                searchText = text,
                filteredPasswords = newFilteredItems
            )
        }
    }

    private fun filterPasswordsByCategory(selected: Boolean, category: Category?) {
        val selectedCategory = if (selected) category else null
        _state.update { currentState ->
            val newFilteredItems = if (selectedCategory == null) {
                currentState.passwords
            } else {
                currentState.passwords.filter { it.tag == category?.name }
            }
            currentState.copy(
                selectedCategory = selectedCategory,
                filteredPasswords = newFilteredItems
            )
        }
    }

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
                    _state.value = _state.value.copy(
                        isLoading = false,
                        passwords = passwordEntities,
                        filteredPasswords = passwordEntities
                    )
                }
            }
        }
    }
}