package com.ab.an.data.network

import com.ab.an.domain.repository.AppSettingsDataStoreRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class CustomInterceptor @Inject constructor(
    private val appDataStoreRepository: AppSettingsDataStoreRepository,
    private val coroutineScope: CoroutineScope
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
            if (originalRequest.header("X-Require-Token") != null) {

                val token = runBlocking(coroutineScope.coroutineContext) { appDataStoreRepository.getJwtToken().firstOrNull() }

                if (token != null) {
                    val requestWithHeader = originalRequest.newBuilder()
                        .header("Authorization", "Bearer $token")
                        .removeHeader("X-Requires-Auth")
                        .build()
                    return chain.proceed(requestWithHeader)
                }
            }
        return  chain.proceed(originalRequest)
    }
}