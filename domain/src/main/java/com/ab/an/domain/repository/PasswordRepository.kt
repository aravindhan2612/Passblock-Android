package com.ab.an.domain.repository

import com.ab.an.core.utils.Resource
import com.ab.an.domain.model.Password
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {

     fun getPasswords(): Flow<Resource<List<Password>>>
     fun addPassword(password: Password): Flow<Resource<Password>>
     fun updatePassword(password: Password): Flow<Resource<Password>>
     fun deletePassword(id: String): Flow<Resource<Password>>
}