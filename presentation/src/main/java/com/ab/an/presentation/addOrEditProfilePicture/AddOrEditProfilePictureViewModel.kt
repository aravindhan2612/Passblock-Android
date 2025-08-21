package com.ab.an.presentation.addOrEditProfilePicture

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.an.core.utils.Resource
import com.ab.an.domain.usecase.DeleteProfilePictureUseCase
import com.ab.an.domain.usecase.GetCurrentUserUseCase
import com.ab.an.domain.usecase.UploadProfilePictureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddOrEditProfilePictureViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val uploadProfilePictureUseCase: UploadProfilePictureUseCase,
    private val deleteProfilePictureUseCase: DeleteProfilePictureUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddOrEditProfilePictureState())
    val state = _state.onStart {
        fetchUser()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        AddOrEditProfilePictureState()
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
                                fullName = it.fullName,
                                profilePicture = it.profilePicture,
                            )
                        }
                    }
                }
            }
        }
    }

    fun onIntent(intent: AddOrEditProfilePictureIntent) {
        when (intent) {
            is AddOrEditProfilePictureIntent.DeleteProfilePicture -> {
                deleteProfilePicture()
            }

            is AddOrEditProfilePictureIntent.UploadProfilePicture -> {
                uploadImage(intent.context, intent.imageUri)
            }
        }
    }

    private fun deleteProfilePicture() {
        viewModelScope.launch {
            deleteProfilePictureUseCase.invoke(state.value.profilePicture).collect { result ->
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
                            profilePicture = ""
                        )
                    }

                    is Resource.Success -> {
                        result.data?.let {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                profilePicture = ""
                            )
                        }
                    }
                }
            }
        }
    }

    private fun uploadImage(context: Context, imageUri: Uri) {
        imageUri.let { uri ->
            viewModelScope.launch {
                // Convert Uri to a byte array
                val inputStream = context.contentResolver.openInputStream(uri)
                val bytes = inputStream?.readBytes() ?: return@launch
                uploadProfilePictureUseCase.invoke(bytes).collect { result ->
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
                                profilePicture = ""
                            )
                        }

                        is Resource.Success -> {

                            result.data?.let {
                                _state.value = _state.value.copy(
                                    isLoading = false,
                                    profilePicture = it.profilePicture
                                )
                            }
                        }
                    }
                }
            }

        }
    }

}