package com.ab.an.presentation.updateContactInfo

import android.content.Context


sealed class UpdateContactInfoIntent {
    data class FullNameChange(val fullName: String): UpdateContactInfoIntent()
    data class EmailChange(val email: String): UpdateContactInfoIntent()
    data class PhoneNumberChange(val phoneNumber: String): UpdateContactInfoIntent()
    data class DateChange(val date: String): UpdateContactInfoIntent()
    data class Submit(val context: Context): UpdateContactInfoIntent()
    data class ImageChange(val base64: String): UpdateContactInfoIntent()
}