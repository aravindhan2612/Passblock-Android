package com.ab.an.presentation.viewPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.an.core.utils.Resource
import com.ab.an.domain.usecase.password.DeletePasswordUseCase
import com.ab.an.domain.usecase.password.GetPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewPasswordViewModel @Inject constructor(
    private val getPasswordUseCase: GetPasswordUseCase,
    private val deletePasswordUseCase: DeletePasswordUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ViewPasswordState())
    val state = _state.asStateFlow()

    fun onIntent(intent: ViewPasswordIntent) {
        when (intent) {
            is ViewPasswordIntent.FetchPassword -> fetchPassword(intent.id)
            ViewPasswordIntent.DeletePassword -> deletePassword()
            ViewPasswordIntent.CopyPassword -> copyPassword()
            ViewPasswordIntent.ChangePassword -> changePassword()
            ViewPasswordIntent.CloseDeleteDialog -> updateDialogState(false)
            ViewPasswordIntent.OpenDeleteDialog -> updateDialogState(true)
        }
    }

    private fun updateDialogState(showDeleteDialog: Boolean) {
        _state.value = _state.value.copy(
            showDeleteDialog = showDeleteDialog
        )
    }

    private fun changePassword() {

    }

    private fun copyPassword() {

    }

    private fun deletePassword() {
        _state.value = _state.value.copy(
            showDeleteDialog = false
        )
        viewModelScope.launch {
            deletePasswordUseCase.invoke(_state.value.password.id).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            deleteError = result.message,
                            isPasswordDeleteSuccess = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true,
                            deleteError = null,
                            isPasswordDeleteSuccess = false
                        )
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            deleteError = null,
                            isPasswordDeleteSuccess = true
                        )
                    }
                }
            }
        }
    }

    private fun fetchPassword(id: String) {
        viewModelScope.launch {
            getPasswordUseCase(id).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true,
                            error = null
                        )
                    }

                    is Resource.Success -> {
                        result.data?.let {
                            _state.value = _state.value.copy(
                                password = it,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                }
            }
        }
    }
}