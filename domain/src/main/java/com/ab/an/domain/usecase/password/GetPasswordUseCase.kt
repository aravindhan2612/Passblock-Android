package com.ab.an.domain.usecase.password

import com.ab.an.domain.repository.PasswordRepository
import javax.inject.Inject

class GetPasswordUseCase @Inject constructor(private val passwordRepository: PasswordRepository) {
    operator fun invoke() = passwordRepository.getPasswords()
    operator fun invoke(id: String) = passwordRepository.getPassword(id)
}