package com.ab.an.data.datastore.serializer

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object AppSettingsSerializer : Serializer<AppSettingsEntity> {
    override val defaultValue: AppSettingsEntity
        get() = AppSettingsEntity()

    override suspend fun readFrom(input: InputStream): AppSettingsEntity {
       return try {
           Json.decodeFromString(
               deserializer = AppSettingsEntity.serializer(),
               string = input.readBytes().decodeToString()
           )
       } catch (e: SerializationException) {
           e.printStackTrace()
           defaultValue
       }
    }

    override suspend fun writeTo(
        t: AppSettingsEntity,
        output: OutputStream
    ) {
        output.write(
            Json.encodeToString(
                serializer = AppSettingsEntity.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}