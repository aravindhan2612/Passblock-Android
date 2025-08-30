package com.ab.an.domain.repository

import com.ab.an.core.utils.Resource
import com.ab.an.domain.model.Password
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {

    fun getPasswords(userId: String): Flow<List<Password>>
    fun fetchPasswords(): Flow<Resource<Boolean>>
    fun getPassword(id: String): Flow<Password?>
    fun addOrUpdatePassword(isEdit: Boolean, password: Password): Flow<Resource<Password>>
    fun deletePassword(id: String): Flow<Resource<Password>>
}