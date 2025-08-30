package com.ab.an.data.impl

import androidx.datastore.core.DataStore
import com.ab.an.data.database.PassblockDatabase
import com.ab.an.data.datastore.serializer.AppSettingsEntity
import com.ab.an.data.mapper.toAppSettings
import com.ab.an.data.mapper.toUser
import com.ab.an.data.mapper.toUserDto
import com.ab.an.domain.model.AppSettings
import com.ab.an.domain.model.User
import com.ab.an.domain.repository.AppSettingsDataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AppSettingsDataStoreImpl(
    private val dataStore: DataStore<AppSettingsEntity>,
    private val passblockDatabase: PassblockDatabase
) :
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

    override fun logout(): Flow<Boolean> = callbackFlow {
        dataStore.updateData { store ->
            store.copy(
                user = User().toUserDto(),
                jwtKey = "",
                isUserLoggedIn = false
            )
        }
        launch(Dispatchers.IO) {
            async { passblockDatabase.clearAllTables() }.await()
            trySend(true)
        }
        awaitClose {}
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
            dataStore.updateData { store ->
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