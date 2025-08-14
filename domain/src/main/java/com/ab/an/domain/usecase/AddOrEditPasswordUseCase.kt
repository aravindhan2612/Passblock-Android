package com.ab.an.domain.usecase

import com.ab.an.domain.model.Password
import com.ab.an.domain.repository.PasswordRepository
import javax.inject.Inject

class AddOrEditPasswordUseCase @Inject constructor(private val passwordRepository: PasswordRepository) {
    operator fun invoke(isEdit: Boolean, password: Password) = if (isEdit) {
        passwordRepository.updatePassword(password)
    } else {
        passwordRepository.addPassword(password)
    }
}