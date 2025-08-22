package com.ab.an.data.datastore.repositoryImpl

import androidx.datastore.core.DataStore
import com.ab.an.data.datastore.serializer.AppSettingsEntity
import com.ab.an.data.mapper.toAppSettings
import com.ab.an.data.mapper.toUser
import com.ab.an.data.mapper.toUserDto
import com.ab.an.domain.model.AppSettings
import com.ab.an.domain.model.User
import com.ab.an.domain.repository.AppSettingsDataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppSettingsDataStoreImpl(private val dataStore: DataStore<AppSettingsEntity>) :
    AppSettingsDataStoreRepository {


    override suspend fun setOnBoardShown(value: Boolean) {
        dataStore.updateData {
            it.copy(
                isOnBoardingShown = value
            )
        }
    }

    override fun getOnBoardShown(): Flow<Boolean> {
        return dataStore.data.map { data -> data.isOnBoardingShown }
    }

    override fun getUser(): Flow<User> {
        return dataStore.data.map { it.user.toUser() }
    }

    override suspend fun setUser(user: User?) {
        user?.let { validUser ->
            dataStore.updateData { store ->
                store.copy(
                    user = validUser.toUserDto()
                )
            }
        }

    }

    override fun getAppSettings(): Flow<AppSettings> {
        return dataStore.data.map { it.toAppSettings() }
    }

    override suspend fun setUserLoggedIn(value: Boolean) {
        dataStore.updateData {
            it.copy(
                isUserLoggedIn = value
            )
        }
    }

    override fun isUserLoggedIn(): Flow<Boolean> {
        return dataStore.data.map { data -> data.isUserLoggedIn }
    }

    override suspend fun saveJwtToken(token: String?) {
        token?.let { validToken ->
            dataStore.updateData { store->
                store.copy(
                    jwtKey = validToken
                )
            }
        }

    }

    override fun getJwtToken(): Flow<String?> {
        return dataStore.data.map { data -> data.jwtKey }
    }
}