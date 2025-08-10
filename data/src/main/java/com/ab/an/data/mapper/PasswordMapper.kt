package com.ab.an.data.mapper

import com.ab.an.data.network.dto.PasswordDto
import com.ab.an.domain.model.PasswordEntity

fun PasswordDto.toPasswordEntity(): PasswordEntity {
    return PasswordEntity(
        id = id ?: "",
        name = name ?: "",
        username = usernameOrUserId ?: "",
        link = websiteLink ?: "",
        password = password ?: "",
        tag = tag ?: "",
        faviconUrl = faviconUrl ?: ""
    )

}

fun PasswordEntity.toPasswordDto(): PasswordDto {
    return PasswordDto(
        id = id,
        name = name,
        usernameOrUserId = username,
        websiteLink = link,
        password = password,
        tag = tag,
        faviconUrl = faviconUrl
    )
}