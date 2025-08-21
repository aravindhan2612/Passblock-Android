package com.ab.an.domain.usecase

import com.ab.an.domain.repository.UserApiRepository
import javax.inject.Inject

class DeleteProfilePictureUseCase @Inject constructor(
    private val userApiRepository: UserApiRepository
) {
    operator fun invoke(filename: String) = userApiRepository.deleteProfilePicture(filename)
}