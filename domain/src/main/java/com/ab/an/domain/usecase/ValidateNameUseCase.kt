package com.ab.an.domain.usecase

import javax.inject.Inject

class ValidateNameUseCase @Inject constructor() {
    operator fun invoke(name: String?, prefixString: String): String {
        return if (name.isNullOrEmpty()) {
            "$prefixString cannot be empty"
        } else if (name.length < 3) {
            "$prefixString must be at least 3 characters long"
        } else {
            ""
        }
    }
}