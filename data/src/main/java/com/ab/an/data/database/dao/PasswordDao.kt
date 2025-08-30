package com.ab.an.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ab.an.data.database.entities.PasswordDBEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {
    @Query("SELECT * FROM passworddbentity WHERE userId = :userId")
    fun getAllPasswords(userId:String): Flow<List<PasswordDBEntity>>

    @Query("SELECT * FROM passworddbentity WHERE id = :id")
    fun getPassword(id:String): Flow<PasswordDBEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPasswords(passwords: List<PasswordDBEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPassword(password: PasswordDBEntity)

    @Update
    suspend fun updatePassword(password: PasswordDBEntity)

    @Query("DELETE FROM passworddbentity WHERE id = :id")
    suspend fun deletePasswordById(id: String): Int?

    @Query("SELECT * FROM passworddbentity WHERE isSynced = 0")
    suspend fun getUnsyncedPasswords(): List<PasswordDBEntity>

    @Query("UPDATE passworddbentity SET isSynced = 1 WHERE id IN (:ids)")
    suspend fun updatePasswordSyncStatus(ids: List<String>)
}