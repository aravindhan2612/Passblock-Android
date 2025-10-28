package com.ab.an.domain.repository

import com.ab.an.domain.model.AppSettings
import com.ab.an.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AppSettingsDataStoreRepository {
    suspend fun setOnBoardShown(value: Boolean)
    fun getOnBoardShown(): Flow<Boolean>

    suspend fun setUserLoggedIn(value: Boolean)
    fun isUserLoggedIn(): Flow<Boolean>

   suspend fun saveJwtToken(token: String?)

    fun getJwtToken(): Flow<String?>

    fun getUser(): Flow<User>

    suspend fun setUser(user: User?)

    fun getAppSettings(): Flow<AppSettings>

    fun logout(): Flow<Boolean>

    suspend fun saveThemeMode(mode: String)

    val themeMode: Flow<String?>
}