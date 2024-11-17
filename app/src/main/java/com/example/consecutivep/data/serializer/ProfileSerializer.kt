package com.example.consecutivep.data.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.example.consecutivep.domain.model.ProfileEntity
import java.io.InputStream
import java.io.OutputStream

object ProfileSerializer : Serializer<ProfileEntity> {
    override val defaultValue: ProfileEntity
        get() = ProfileEntity.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): ProfileEntity {
        try {
            return ProfileEntity.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto", e)
        }
    }

    override suspend fun writeTo(t: ProfileEntity, output: OutputStream) {
        t.writeTo(output)
    }
}