package com.ab.an.data.mapper

import com.ab.an.core.utils.DateUtils
import com.ab.an.data.network.dto.UserDto
import com.ab.an.domain.model.User

fun UserDto.toUser(): User {
    return User(
        email = email ?: "",
        fullName = fullName ?: "",
        password = password ?: "",
        phoneNumber = phoneNumber ?: "",
        dob = DateUtils.fromIsoToCustom(dob),
        profilePicture = profilePicture ?: ""
    )
}

fun User.toUserDto(): UserDto {
    return UserDto(
        email = email,
        fullName = fullName,
        password = password,
        phoneNumber = phoneNumber,
        dob = DateUtils.fromCustomToIso(dob),
        profilePicture = profilePicture
    )
}