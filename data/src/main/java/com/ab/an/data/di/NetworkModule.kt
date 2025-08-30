package com.ab.an.data.di

import com.ab.an.core.utils.Constants
import com.ab.an.data.network.CustomInterceptor
import com.ab.an.data.network.api.PasswordApiService
import com.ab.an.data.network.api.UserApiService
import com.ab.an.domain.repository.AppSettingsDataStoreRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.IO + SupervisorJob())
    }

    /**Te
     * Provides a Gson instance for JSON serialization and deserialization.
     * We use `setLenient()` to allow for more flexible JSON parsing, which can be
     * helpful during development with less strict APIs.
     *
     * @return A configured Gson instance.
     */
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    /**
     * Provides an HttpLoggingInterceptor for logging network requests and responses.
     * This is extremely useful for debugging API calls during development.
     * The `BODY` level logs headers, bodies, and metadata.
     *
     * @return An HttpLoggingInterceptor instance.
     */
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideCustomInterceptor(appDataStoreRepository: AppSettingsDataStoreRepository, coroutineScope: CoroutineScope): CustomInterceptor {
        return CustomInterceptor(appDataStoreRepository, coroutineScope)
    }

    /**
     * Provides an OkHttpClient instance. OkHttpClient is the underlying HTTP client
     * that Retrofit uses to make network requests. Here, we configure it with
     * the logging interceptor and various timeouts.
     *
     * @param loggingInterceptor The HttpLoggingInterceptor provided by this module.
     * @return A configured OkHttpClient instance.
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        customInterceptor: CustomInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(customInterceptor)// Add the logging interceptor for debugging
            .connectTimeout(30, TimeUnit.SECONDS) // Sets the maximum time to establish a connection
            .readTimeout(
                30,
                TimeUnit.SECONDS
            )    // Sets the maximum time to read data from the server
            .writeTimeout(
                30,
                TimeUnit.SECONDS
            )   // Sets the maximum time to write data to the server
            .build()
    }

    /**
     * Provides a GsonConverterFactory. This factory tells Retrofit how to convert
     * Kotlin objects to JSON for requests and JSON responses back into Kotlin objects.
     *
     * @param gson The Gson instance provided by this module.
     * @return A GsonConverterFactory instance.
     */
    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.E_BASE_URL) // Set the base URL for all API calls
            .client(okHttpClient) // Set the HTTP client
            .addConverterFactory(gsonConverterFactory) // Add the JSON converter
            .build()
    }

    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePasswordApiService(retrofit: Retrofit): PasswordApiService {
        return retrofit.create(PasswordApiService::class.java)
    }
}