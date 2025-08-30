package com.ab.an.domain.usecase.user

import com.ab.an.core.utils.Resource
import com.ab.an.domain.model.User
import com.ab.an.domain.repository.UserApiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRegisterUseCase @Inject constructor(private val userRepository: UserApiRepository) {

    operator fun invoke(user: User): Flow<Resource<User>> = userRepository.register(user)
}