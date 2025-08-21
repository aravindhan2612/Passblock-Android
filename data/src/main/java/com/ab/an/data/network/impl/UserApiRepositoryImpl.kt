package com.ab.an.data.network.impl

import android.util.Log
import com.ab.an.core.utils.Resource
import com.ab.an.data.mapper.toUser
import com.ab.an.data.mapper.toUserDto
import com.ab.an.data.network.api.UserApiService
import com.ab.an.domain.model.User
import com.ab.an.domain.repository.AppDataStoreRepository
import com.ab.an.domain.repository.UserApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.UUID
import javax.inject.Inject

class UserApiRepositoryImpl @Inject constructor(
    private val apiService: UserApiService,
    private val appDataStoreRepository: AppDataStoreRepository
) : UserApiRepository, BaseRepositoryImpl() {

    override fun register(user: User): Flow<Resource<User>> = flow {

        getResult {
            val postDto = user.toUserDto()
            apiService.register(postDto)
        }.collect { resource ->
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

    override fun getCurrentUser(): Flow<Resource<User>> = flow {
        getResult { apiService.getCurrentUser() }.collect { resource ->
            when (resource) {
                is Resource.Error -> {
                    emit(Resource.Error(resource.message))
                }

                is Resource.Loading -> {
                    emit(Resource.Loading())
                }

                is Resource.Success -> {
                    emit(Resource.Success(resource.data?.toUser()))
                }
            }
        }
    }

    override fun updateUserProfile(user: User): Flow<Resource<User>> = flow {
        getResult { apiService.updateUserProfile(user.toUserDto()) }.collect { resource ->
            when (resource) {
                is Resource.Error -> {
                    emit(Resource.Error(resource.message))
                }
                is Resource.Loading -> {
                    emit(Resource.Loading())
                }
                is Resource.Success -> {
                    emit(Resource.Success(resource.data?.toUser()))
                }
            }
        }
    }

    override fun uploadProfilePicture(profilePicture: ByteArray): Flow<Resource<User>> = flow {
        val requestFile = profilePicture.toRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("profilePicture", "${UUID.randomUUID()}.jpg", requestFile)
        getResult { apiService.uploadProfilePicture(body) }.collect { resource ->
            when (resource) {
                is Resource.Error -> {
                    emit(Resource.Error(resource.message))
                }
                is Resource.Loading -> {
                    emit(Resource.Loading())
                }
                is Resource.Success -> {
                    emit(Resource.Success(resource.data?.toUser()))
                }
            }
        }
    }

    override fun deleteProfilePicture(filename: String): Flow<Resource<User>> = flow {
        getResult { apiService.deleteProfilePicture(filename) }.collect { resource ->
            when (resource){
                is Resource.Error -> {
                    emit(Resource.Error(resource.message))
                }
                is Resource.Loading -> {
                    emit(Resource.Loading())
                }
                is Resource.Success -> {
                    emit(Resource.Success(resource.data?.toUser()))
                }
            }
        }
    }

    private suspend fun saveAppData(token: String?) {
        appDataStoreRepository.setUserLoggedIn(true)
        appDataStoreRepository.saveJwtToken(token)
    }
}