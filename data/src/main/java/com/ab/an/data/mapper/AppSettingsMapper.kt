package com.ab.an.data.mapper

import com.ab.an.data.datastore.serializer.AppSettingsEntity
import com.ab.an.domain.model.AppSettings


fun AppSettings.toAppSettingsEntity(): AppSettingsEntity {
    return AppSettingsEntity(
        isOnBoardingShown = isOnBoardingShown,
        isUserLoggedIn = isUserLoggedIn,
        jwtKey = jwtKey,
        user = user.toUserDto()
    )
}

fun AppSettingsEntity.toAppSettings(): AppSettings {
    return AppSettings(
        isOnBoardingShown = isOnBoardingShown,
        isUserLoggedIn = isUserLoggedIn,
        jwtKey = jwtKey,
        user = user.toUser()
    )
}