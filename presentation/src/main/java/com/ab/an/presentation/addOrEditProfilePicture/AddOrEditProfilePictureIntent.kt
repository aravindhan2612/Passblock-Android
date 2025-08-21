package com.ab.an.presentation.addOrEditProfilePicture

import android.content.Context
import android.net.Uri

sealed class AddOrEditProfilePictureIntent {

    object DeleteProfilePicture : AddOrEditProfilePictureIntent()
    data class UploadProfilePicture(val context: Context, val imageUri: Uri) :
        AddOrEditProfilePictureIntent()

}