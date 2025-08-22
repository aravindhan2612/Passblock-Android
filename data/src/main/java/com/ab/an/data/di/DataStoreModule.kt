package com.ab.an.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.ab.an.data.datastore.repositoryImpl.AppSettingsDataStoreImpl
import com.ab.an.data.datastore.serializer.AppSettingsEntity
import com.ab.an.data.datastore.serializer.AppSettingsSerializer
import com.ab.an.domain.repository.AppSettingsDataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


val Context.dataStore by dataStore("app_settings.json", AppSettingsSerializer)
@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<AppSettingsEntity> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideAppDataStoreRepository(dataStore: DataStore<AppSettingsEntity>): AppSettingsDataStoreRepository {
        return AppSettingsDataStoreImpl(dataStore)
    }
}