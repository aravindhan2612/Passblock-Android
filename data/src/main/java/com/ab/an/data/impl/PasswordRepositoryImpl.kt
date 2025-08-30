package com.ab.an.data.impl

import com.ab.an.core.utils.Resource
import com.ab.an.data.database.dao.PasswordDao
import com.ab.an.data.mapper.toPassword
import com.ab.an.data.mapper.toPasswordDBEntity
import com.ab.an.data.mapper.toPasswordDto
import com.ab.an.data.mapper.toPasswordEntity
import com.ab.an.data.mapper.toPasswordEntityList
import com.ab.an.data.mapper.toPasswordList
import com.ab.an.data.network.api.PasswordApiService
import com.ab.an.domain.model.Password
import com.ab.an.domain.repository.PasswordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PasswordRepositoryImpl @Inject constructor(
    private val apiService: PasswordApiService,
    private val passwordDao: PasswordDao,
) :
    PasswordRepository, BaseApiRepositoryImpl() {
    override fun getPasswords(userId: String): Flow<List<Password>> =
        passwordDao.getAllPasswords(userId).map { passwords ->
            passwords.toPasswordList()
        }

    override fun fetchPasswords(): Flow<Resource<Boolean>> = flow {
        getResult { apiService.getPasswords() }.collect { response ->
            when (response) {
                is Resource.Error -> emit(Resource.Error(response.message))
                is Resource.Loading -> emit(Resource.Loading())
                is Resource.Success -> {
                    response.data?.toPasswordEntityList()?.let { passwordDao.insertPasswords(it) }
                    emit(Resource.Success(true))
                }
            }
        }
    }

    override fun getPassword(id: String): Flow<Password?> =
        passwordDao.getPassword(id).map { it?.toPassword() }

    override fun addOrUpdatePassword(
        isEdit: Boolean,
        password: Password
    ): Flow<Resource<Password>> {
        return if (isEdit) {
            updatePassword(password)
        } else {
            addPassword(password)
        }
    }

    override fun deletePassword(id: String): Flow<Resource<Password>> = flow {
        getResult { apiService.deletePassword(id) }.collect { resource ->
            when (resource) {
                is Resource.Error -> emit(Resource.Error(resource.message))
                is Resource.Loading -> emit(Resource.Loading())
                is Resource.Success -> {
                    passwordDao.deletePasswordById(id)
                    emit(Resource.Success(resource.data?.toPasswordEntity()))
                }
            }
        }
    }

    private fun addPassword(password: Password): Flow<Resource<Password>> = flow {
        getResult { apiService.addPassword(password.toPasswordDto()) }.collect { response ->
            when (response) {
                is Resource.Error -> emit(Resource.Error(response.message))
                is Resource.Loading -> emit(Resource.Loading())
                is Resource.Success -> {
                    response.data?.let { passwordDao.insertPassword(it.toPasswordDBEntity()) }
                    emit(Resource.Success(response.data?.toPasswordEntity()))
                }
            }
        }
    }

    private fun updatePassword(password: Password): Flow<Resource<Password>> = flow {
        getResult {
            apiService.updatePassword(
                password.id,
                password.toPasswordDto()
            )
        }.collect { response ->
            when (response) {
                is Resource.Error -> emit(Resource.Error(response.message))
                is Resource.Loading -> emit(Resource.Loading())
                is Resource.Success -> {
                    response.data?.let { passwordDao.updatePassword(it.toPasswordDBEntity()) }
                    emit(Resource.Success(response.data?.toPasswordEntity()))
                }
            }
        }
    }
}