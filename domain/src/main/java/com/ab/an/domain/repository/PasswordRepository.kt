package com.ab.an.domain.repository

import com.ab.an.core.utils.Resource
import com.ab.an.domain.model.PasswordEntity
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {

     fun getPasswords(): Flow<Resource<List<PasswordEntity>>>
     fun addPassword(password: PasswordEntity): Flow<Resource<PasswordEntity>>
     fun updatePassword(password: PasswordEntity): Flow<Resource<PasswordEntity>>
     fun deletePassword(id: String): Flow<Resource<PasswordEntity>>
}