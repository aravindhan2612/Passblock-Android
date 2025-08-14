package com.ab.an.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.an.core.utils.Resource
import com.ab.an.domain.usecase.GetPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPasswordsUseCase: GetPasswordUseCase
) : ViewModel() {


    private val _state = MutableStateFlow(HomeState())
    val state = _state.onStart {
        fetchPasswords()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        HomeState()
    )

    fun fetchPasswords() {
        viewModelScope.launch {
            getPasswordsUseCase.invoke().collect { result ->
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
                        val passwordEntities = result.data
                        val sectionListItems = mutableListOf<PasswordSectionListItem>()
                        passwordEntities?.groupBy { passwordEntity ->
                            passwordEntity.tag
                        }?.forEach { (tag, passwords) ->
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
}