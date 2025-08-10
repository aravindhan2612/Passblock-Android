package com.ab.an.domain.usecase

import android.util.Patterns
import javax.inject.Inject

class ValidateLinkUseCase @Inject constructor() {
    operator fun invoke(url: String?): String {
        return if( !url.isNullOrBlank() &&!Patterns.WEB_URL.matcher(url).matches()) {
            "Invalid URL"
        } else {
            ""
        }
    }
}