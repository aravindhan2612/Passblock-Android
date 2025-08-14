package com.ab.an.domain.usecase

import com.ab.an.domain.repository.PasswordRepository
import javax.inject.Inject

class DeletePasswordUseCase @Inject constructor(private val passwordRepository: PasswordRepository) {
    operator fun invoke(id: String) = passwordRepository.deletePassword(id)
}