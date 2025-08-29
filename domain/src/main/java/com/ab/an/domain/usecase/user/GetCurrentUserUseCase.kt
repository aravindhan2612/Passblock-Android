package com.ab.an.domain.usecase.user

import com.ab.an.domain.repository.UserApiRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val userApiRepository: UserApiRepository
) {
    operator fun invoke() = userApiRepository.getCurrentUser()

}