package com.ab.an.data.mapper

import com.ab.an.data.network.dto.UserDto
import com.ab.an.domain.model.User

fun UserDto.toUser(): User {
    return User(
        email = email ?: "",
        fullName = fullName ?: "",
        password = password ?: "" // Map DTO field to domain entity field
    )
}

fun User.toUserDto(): UserDto {
    return UserDto(
        email = email,
        fullName = fullName,
        password = password /// Or another appropriate field
    )
}