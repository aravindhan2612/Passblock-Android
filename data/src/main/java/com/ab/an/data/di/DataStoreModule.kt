package com.ab.an.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.ab.an.core.utils.userDataStore
import com.ab.an.data.datastore.repositoryImpl.AppDataStoreImpl
import com.ab.an.domain.repository.AppDataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.userDataStore
    }

    @Provides
    @Singleton
    fun bindAppDataStoreRepository(dataStore: DataStore<Preferences>): AppDataStoreRepository {
        return AppDataStoreImpl(dataStore)
    }
}