package com.ab.an.domain.usecase

import com.ab.an.domain.model.Password
import com.ab.an.domain.repository.PasswordRepository
import javax.inject.Inject

class AddPasswordUseCase @Inject constructor(private val passwordRepository: PasswordRepository) {
    operator fun invoke(password: Password) = passwordRepository.addPassword(password)
}