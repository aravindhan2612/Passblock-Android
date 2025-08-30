package com.ab.an.domain.usecase.user

import com.ab.an.domain.repository.UserApiRepository
import javax.inject.Inject

class UploadProfilePictureUseCase @Inject constructor(
    private val userApiRepository: UserApiRepository
) {
    operator fun invoke(profilePicture: ByteArray) = userApiRepository.uploadProfilePicture(profilePicture)
}