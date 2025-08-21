package com.ab.an.domain.repository

import com.ab.an.core.utils.Resource
import com.ab.an.domain.model.Password
import com.ab.an.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserApiRepository {

    fun register(user: User): Flow<Resource<User>>

    fun login(user: User): Flow<Resource<User>>

    fun getCurrentUser(): Flow<Resource<User>>

    fun updateUserProfile(user: User): Flow<Resource<User>>

    fun uploadProfilePicture(profilePicture: ByteArray): Flow<Resource<User>>
    fun deleteProfilePicture(filename: String): Flow<Resource<User>>
}