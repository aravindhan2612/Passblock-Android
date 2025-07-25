package com.ab.an.data.datastore.repositoryImpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ab.an.domain.repository.AppDataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppDataStoreImpl(private val dataStore: DataStore<Preferences>) :
    AppDataStoreRepository {

    companion object {
        private val onBoardKey = booleanPreferencesKey("isOnBoardingShown")
        private val loggedInKey = booleanPreferencesKey("isUserLoggedIn")
        private val jwtKey = stringPreferencesKey("jwt_token")
    }

    override suspend fun setOnBoardShown(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[onBoardKey] = value
        }
    }

    override fun getOnBoardShown(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[onBoardKey] == true
        }
    }

    override suspend fun setUserLoggedIn(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[loggedInKey] = value
        }
    }

    override fun isUserLoggedIn(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[loggedInKey] == true
        }
    }

    override suspend fun saveJwtToken(token: String?) {
        token?.let { validToken ->
            dataStore.edit { preferences ->
                preferences[jwtKey] = validToken
            }
        }
    }

    override fun getJwtToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[jwtKey]
        }
    }
}