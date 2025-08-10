package com.ab.an.data.network.impl

import com.ab.an.core.utils.Resource
import com.ab.an.data.utils.ApiHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepositoryImpl {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Flow<Resource<T>> = flow {
        emit(Resource.Loading())
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(Resource.Success(body))
                } else {
                    emit(Resource.Error("API Error: ${response.code()} - Empty response body"))
                }
            } else {
                val errorMessage = ApiHelper.parseError(response)
                emit(Resource.Error("API Error: ${response.code()} - $errorMessage"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("HTTP Error: ${e.localizedMessage ?: "An unexpected HTTP error occurred"}"))
        } catch (e: IOException) {
            emit(Resource.Error("Network Error: Please check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error("An unknown error occurred: ${e.localizedMessage ?: "Unknown error"}"))
        }
    }
}