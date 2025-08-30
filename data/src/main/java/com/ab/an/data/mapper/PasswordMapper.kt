package com.ab.an.data.mapper

import com.ab.an.data.database.entities.PasswordDBEntity
import com.ab.an.data.network.dto.PasswordDto
import com.ab.an.domain.model.Password

fun PasswordDto.toPasswordEntity(): Password {
    return Password(
        id = id ?: "",
        name = name ?: "",
        username = usernameOrUserId ?: "",
        link = websiteLink ?: "",
        password = password ?: "",
        tag = tag ?: "",
        faviconUrl = faviconUrl ?: ""
    )

}

fun Password.toPasswordDto(): PasswordDto {
    return PasswordDto(
        id = id,
        name = name,
        usernameOrUserId = username,
        websiteLink = link,
        password = password,
        tag = tag,
        faviconUrl = faviconUrl,
        userId = ""
    )
}

fun PasswordDto.toPasswordDBEntity(): PasswordDBEntity {
    return PasswordDBEntity(
        id = id ?: "",
        name = name,
        usernameOrUserId = usernameOrUserId,
        websiteLink = websiteLink,
        password = password,
        tag = tag,
        faviconUrl = faviconUrl,
        userId = userId
    )
}

fun PasswordDBEntity.toPassword(): Password {
    return Password(
        id = id,
        name = name ?: "",
        username = usernameOrUserId ?: "",
        link = websiteLink ?: "",
        password = password ?: "",
        tag = tag ?: "",
        faviconUrl = faviconUrl ?: ""
    )
}

fun List<PasswordDBEntity>.toPasswordList(): List<Password> {
    return map { it.toPassword() }
}

fun List<PasswordDto>.toPasswordEntityList(): List<PasswordDBEntity> {
    return map { it.toPasswordDBEntity()}
}