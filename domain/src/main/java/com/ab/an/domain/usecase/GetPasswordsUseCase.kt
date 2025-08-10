package com.ab.an.domain.usecase

import com.ab.an.domain.repository.PasswordRepository
import javax.inject.Inject

class GetPasswordsUseCase @Inject constructor(private val passwordRepository: PasswordRepository) {
    operator fun invoke() = passwordRepository.getPasswords()
}