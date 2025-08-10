package com.ab.an.domain.usecase

import com.ab.an.domain.model.PasswordEntity
import com.ab.an.domain.repository.PasswordRepository
import javax.inject.Inject

class AddPasswordUseCase @Inject constructor(private val passwordRepository: PasswordRepository) {
    operator fun invoke(password: PasswordEntity) = passwordRepository.addPassword(password)
}