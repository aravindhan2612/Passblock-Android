package com.ab.an.domain.usecase.validate

import android.util.Patterns
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {

    operator fun invoke(email: String?): String {
        return if (email.isNullOrEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email)
                .matches()
        ) {
            "Invalid email format"
        } else {
            ""
        }
    }
}