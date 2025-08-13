package com.ab.an.data.network.impl

import com.ab.an.core.utils.Resource
import com.ab.an.data.mapper.toPasswordDto
import com.ab.an.data.mapper.toPasswordEntity
import com.ab.an.data.network.api.PasswordApiService
import com.ab.an.domain.model.Password
import com.ab.an.domain.repository.PasswordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PasswordRepositoryImpl @Inject constructor(private val apiService: PasswordApiService) :
    PasswordRepository, BaseRepositoryImpl() {
    override fun getPasswords(): Flow<Resource<List<Password>>> = flow {
        getResult { apiService.getPasswords() }.collect { resource ->
            when (resource) {
                is Resource.Error -> emit(Resource.Error(resource.message))
                is Resource.Loading -> emit(Resource.Loading())
                is Resource.Success -> emit(Resource.Success(resource.data?.map { passwordDto ->  passwordDto.toPasswordEntity()}))
            }
        }
    }

    override fun addPassword(password: Password): Flow<Resource<Password>> = flow {
        getResult { apiService.addPassword(password.toPasswordDto()) }.collect { resource ->
            when (resource) {
                is Resource.Error -> emit(Resource.Error(resource.message))
                is Resource.Loading -> emit(Resource.Loading())
                is Resource.Success -> emit(Resource.Success(resource.data?.toPasswordEntity()))
            }
        }
    }

    override fun updatePassword(password: Password): Flow<Resource<Password>> = flow {
        getResult {
            apiService.updatePassword(
                password.id,
                password.toPasswordDto()
            )
        }.collect { resource ->
            when (resource) {
                is Resource.Error -> emit(Resource.Error(resource.message))
                is Resource.Loading -> emit(Resource.Loading())
                is Resource.Success -> emit(Resource.Success(resource.data?.toPasswordEntity()))
            }
        }
    }

    override fun deletePassword(id: String): Flow<Resource<Password>> = flow {
        getResult { apiService.deletePassword(id) }.collect { resource ->
            when (resource) {
                is Resource.Error -> emit(Resource.Error(resource.message))
                is Resource.Loading -> emit(Resource.Loading())
                is Resource.Success -> emit(Resource.Success(resource.data?.toPasswordEntity()))
            }
        }
    }
}