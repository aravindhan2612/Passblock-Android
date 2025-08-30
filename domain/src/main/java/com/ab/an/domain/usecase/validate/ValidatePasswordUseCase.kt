package com.ab.an.domain.usecase.validate

import java.util.regex.Pattern
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor()  {

    private val UPPER_CASE_REGEX = ".*[A-Z].*"

    // Regex for at least one lowercase letter
    private val LOWER_CASE_REGEX = ".*[a-z].*"

    // Regex for at least one special character
    // This includes common special characters. You can adjust this set as needed.
    private val SPECIAL_CHAR_REGEX = ".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?`~].*"

    // Minimum password length
    private val MIN_LENGTH = 8

    operator fun invoke(password: String?): String {
        if (password.isNullOrEmpty()) {
            return "Password cannot be empty."
        }

        if (password.length < MIN_LENGTH) {
            return "Password must be at least $MIN_LENGTH characters long."
        }

        if (!Pattern.matches(UPPER_CASE_REGEX, password)) {
            return "Password must contain at least one uppercase letter."
        }

        if (!Pattern.matches(LOWER_CASE_REGEX, password)) {
            return "Password must contain at least one lowercase letter."
        }

        if (!Pattern.matches(SPECIAL_CHAR_REGEX, password)) {
            return "Password must contain at least one special character."
        }

        return ""
    }
}