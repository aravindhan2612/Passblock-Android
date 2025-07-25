package com.ab.an.domain.repository

import kotlinx.coroutines.flow.Flow

interface AppDataStoreRepository {
    suspend fun setOnBoardShown(value: Boolean)
    fun getOnBoardShown(): Flow<Boolean>

    suspend fun setUserLoggedIn(value: Boolean)
    fun isUserLoggedIn(): Flow<Boolean>
}