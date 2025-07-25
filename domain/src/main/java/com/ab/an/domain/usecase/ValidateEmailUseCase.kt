package com.ab.an.domain.usecase

import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {

    operator fun invoke(email: String?): String {
        return if (email.isNullOrEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches()
        ) {
            "Invalid email format"
        } else {
            ""
        }
    }
}