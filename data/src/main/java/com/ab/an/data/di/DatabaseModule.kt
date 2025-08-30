package com.ab.an.data.di

import android.content.Context
import androidx.room.Room
import com.ab.an.data.database.PassblockDatabase
import com.ab.an.data.database.dao.PasswordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun providePassblockDatabase(@ApplicationContext context: Context): PassblockDatabase {
        return Room.databaseBuilder(
            context,
            PassblockDatabase::class.java,
            "passblock_database"
        ).build()
    }

    @Provides
    @Singleton
    fun providePasswordDao(passblockDatabase: PassblockDatabase): PasswordDao {
        return passblockDatabase.passwordDao()
    }
}