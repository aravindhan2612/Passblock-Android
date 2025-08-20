package com.ab.an.domain.usecase

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import jakarta.inject.Inject

class ValidatePhoneNumberUseCase @Inject constructor() {
    operator fun invoke(number: String, region: String): String {
        if (number.isBlank()) return ""
        val phoneUtil = PhoneNumberUtil.getInstance()
        return try {
            val phoneNumber = phoneUtil.parse(number, region)
            if (phoneUtil.isValidNumber(phoneNumber)) "" else "Invalid number"
        } catch (e: NumberParseException) {
            ""
        }
    }
}