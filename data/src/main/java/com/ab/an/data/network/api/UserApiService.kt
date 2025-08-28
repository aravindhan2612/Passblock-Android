package com.ab.an.data.network.api

import com.ab.an.data.network.dto.AuthResponseDto
import com.ab.an.data.network.dto.UpdatePasswordDto
import com.ab.an.data.network.dto.UserDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

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

    @Multipart
    @Headers("X-Require-Token: true")
    @POST("uploads/upload-profile-picture")
    suspend fun uploadProfilePicture(
        @Part profilePicture: MultipartBody.Part
    ): Response<UserDto>


    @Headers("X-Require-Token: true")
    @DELETE("uploads/{filename}")
    suspend fun deleteProfilePicture(
        @Path("filename") filename: String
    ): Response<UserDto>

    @Headers("X-Require-Token: true")
    @POST("api/user/updatePassword")
    suspend fun updateUserPassword(
        @Body updatePasswordDto: UpdatePasswordDto
    ): Response<UserDto>
}