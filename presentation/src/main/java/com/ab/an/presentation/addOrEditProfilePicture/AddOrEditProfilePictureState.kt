package com.ab.an.presentation.addOrEditProfilePicture

data class AddOrEditProfilePictureState(
    val fullName: String = "",
    val profilePicture: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
