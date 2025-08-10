package com.ab.an.data.network.impl

import com.ab.an.core.utils.Resource
import com.ab.an.data.mapper.toUserDto
import com.ab.an.data.network.api.UserApiService
import com.ab.an.domain.model.User
import com.ab.an.domain.repository.AppDataStoreRepository
import com.ab.an.domain.repository.UserApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserApiRepositoryImpl @Inject constructor(
    private val apiService: UserApiService,
    private val appDataStoreRepository: AppDataStoreRepository
) : UserApiRepository, BaseRepositoryImpl() {

    override fun register(user: User): Flow<Resource<User>> = flow {

        getResult {
            val postDto = user.toUserDto()
            apiService.register(postDto)
        }.collect {  resource ->
            when (resource) {
                is Resource.Error -> {
                    emit(Resource.Error(resource.message))
                }
                is Resource.Loading -> {
                    emit(Resource.Loading())
                }
                is Resource.Success -> {
                    saveAppData(resource.data?.token)
                    emit(Resource.Success(user))
                }
            }
        }
    }

    override fun login(
        user: User
    ): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        getResult {
            // Map domain model to DTO for the API request
            apiService.login(user.toUserDto().copy(fullName = null))
        }.collect { resource ->
            when(resource) {
                is Resource.Error -> {
                    emit(Resource.Error(resource.message))
                }
                is Resource.Loading -> {
                    emit(Resource.Loading())
                }
                is Resource.Success -> {
                    saveAppData(resource.data?.token)
                    emit(Resource.Success(user))
                }
            }
        }
    }

    private suspend fun saveAppData(token: String?) {
        appDataStoreRepository.setUserLoggedIn(true)
        appDataStoreRepository.saveJwtToken(token)
    }
}