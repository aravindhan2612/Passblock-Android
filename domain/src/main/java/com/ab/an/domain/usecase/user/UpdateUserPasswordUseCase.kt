package com.ab.an.domain.usecase.user

import com.ab.an.domain.repository.UserApiRepository
import javax.inject.Inject

class UpdateUserPasswordUseCase @Inject constructor(
    private val userApiRepository: UserApiRepository
) {
    operator fun invoke(currentPassword: String, newPassword: String) =
        userApiRepository.updatePassword(currentPassword, newPassword)
}