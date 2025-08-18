package com.ab.an.data.network.api

import com.ab.an.data.network.dto.AuthResponseDto
import com.ab.an.data.network.dto.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserApiService {

    @POST("api/auth/register") // Example endpoint for sign-in
    suspend fun register(@Body request: UserDto): Response<AuthResponseDto>

    @POST("api/auth/login") // Example endpoint for sign-up
    suspend fun login(@Body request: UserDto): Response<AuthResponseDto>

    @Headers("X-Require-Token: true")
    @GET("api/auth/me") // Example endpoint for sign-up
    suspend fun getCurrentUser(): Response<UserDto>
}