package com.ab.an.data.network.api

import com.ab.an.data.network.dto.PasswordDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PasswordApiService {

    @Headers("X-Require-Token: true")
    @GET("api/passwords")
    suspend fun getPasswords(): Response<List<PasswordDto>>

    @Headers("X-Require-Token: true")
    @POST("api/passwords/add")
    suspend fun addPassword(@Body password: PasswordDto): Response<PasswordDto>
    @Headers("X-Require-Token: true")
    @PUT("api/passwords/update/{id}")
    suspend fun updatePassword(@Path("id") id: String, @Body password: PasswordDto): Response<PasswordDto>

    @Headers("X-Require-Token: true")
    @DELETE("api/passwords/delete/{id}")
    suspend fun deletePassword(@Path("id") id: String): Response<PasswordDto>

}