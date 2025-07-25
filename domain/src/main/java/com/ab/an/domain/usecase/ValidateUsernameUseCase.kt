package com.ab.an.domain.usecase

import javax.inject.Inject

class ValidateUsernameUseCase @Inject constructor()    {
    operator fun invoke(username: String?): String {
        return if (username.isNullOrEmpty()) {
            "Username cannot be empty"
        } else if (username.length <= 3) {
            "Username must be at least 3 characters long"
        } else {
            ""
        }
    }
}