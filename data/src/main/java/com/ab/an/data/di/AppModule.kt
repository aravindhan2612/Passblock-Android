package com.ab.an.data.di

import androidx.datastore.core.DataStore
import com.ab.an.data.database.PassblockDatabase
import com.ab.an.data.database.dao.PasswordDao
import com.ab.an.data.impl.AppSettingsDataStoreImpl
import com.ab.an.data.datastore.serializer.AppSettingsEntity
import com.ab.an.data.impl.PasswordRepositoryImpl
import com.ab.an.data.impl.UserApiRepositoryImpl
import com.ab.an.data.network.api.PasswordApiService
import com.ab.an.data.network.api.UserApiService
import com.ab.an.domain.repository.AppSettingsDataStoreRepository
import com.ab.an.domain.repository.PasswordRepository
import com.ab.an.domain.repository.UserApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideAppDataStoreRepository(dataStore: DataStore<AppSettingsEntity>, passblockDatabase: PassblockDatabase): AppSettingsDataStoreRepository {
        return AppSettingsDataStoreImpl(dataStore,passblockDatabase)
    }
    @Provides
    @Singleton
    fun provideUserApiRepository(userApiService: UserApiService, appDataStore: AppSettingsDataStoreRepository): UserApiRepository {
        return UserApiRepositoryImpl(userApiService, appDataStore)
    }

    @Provides
    @Singleton
    fun providePasswordRepository(passwordApiService: PasswordApiService,passwordDao: PasswordDao): PasswordRepository {
        return PasswordRepositoryImpl(passwordApiService, passwordDao)
    }
}