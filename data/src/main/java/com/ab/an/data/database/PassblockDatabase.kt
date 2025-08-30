package com.ab.an.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ab.an.data.database.dao.PasswordDao
import com.ab.an.data.database.entities.PasswordDBEntity

@Database(
    entities = [PasswordDBEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PassblockDatabase: RoomDatabase() {
    abstract fun passwordDao(): PasswordDao
}