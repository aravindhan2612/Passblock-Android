package com.ab.an.data.network.api

import com.ab.an.data.network.dto.AuthResponseDto
import com.ab.an.data.network.dto.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserApiService {

    @POST("api/user/register") // Example endpoint for sign-in
    suspend fun register(@Body request: UserDto): Response<AuthResponseDto>

    @POST("api/user/login") // Example endpoint for sign-up
    suspend fun login(@Body request: UserDto): Response<AuthResponseDto>

    @Headers("X-Require-Token: true")
    @GET("api/user/me") // Example endpoint for sign-up
    suspend fun getCurrentUser(): Response<UserDto>

    @Headers("X-Require-Token: true")
    @POST("api/user/updateUser") // Example endpoint for sign-up
    suspend fun updateUserProfile(@Body request: UserDto): Response<UserDto>
}