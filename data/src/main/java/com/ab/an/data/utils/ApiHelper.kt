package com.ab.an.data.utils

import com.google.gson.Gson
import retrofit2.Response

object ApiHelper {

    fun parseError(response: Response<*>): String? {
        val errorBody = response.errorBody()?.string()
        return try {
            val gson = Gson()
            val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
            errorResponse.error
        } catch (e: Exception) {
            "Unknown error"
        }
    }
}