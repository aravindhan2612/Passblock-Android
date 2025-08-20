package com.ab.an.domain.usecase

import com.ab.an.domain.model.User
import com.ab.an.domain.repository.UserApiRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userApiRepository: UserApiRepository
) {
    suspend operator fun invoke(user: User) = userApiRepository.updateUserProfile(user)
}