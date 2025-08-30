package com.ab.an.domain.usecase.user

import com.ab.an.domain.model.User
import com.ab.an.domain.repository.UserApiRepository
import javax.inject.Inject

class UserLoginUseCase @Inject constructor(private val userRepository: UserApiRepository)  {
    operator fun invoke(user: User) = userRepository.login(user)
}