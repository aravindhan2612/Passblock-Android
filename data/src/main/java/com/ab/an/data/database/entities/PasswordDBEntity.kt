package com.ab.an.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PasswordDBEntity(
    @PrimaryKey val id: String,
    val name: String?,
    val password: String?,
    val tag: String?,
    val usernameOrUserId: String?,
    val websiteLink: String?,
    val faviconUrl : String?,
    var isSynced: Boolean = true,
    var userId: String
)
