package com.example.consecutivep.domain.repository

import kotlinx.coroutines.flow.Flow
import com.example.consecutivep.domain.model.ProfileEntity
import com.example.consecutivep.domain.model.ProfileEntityKt

interface IProfileRepository {
    suspend fun getProfile(): ProfileEntity?
    suspend fun setProfile(photoUri: String, name: String, url: String): ProfileEntity
    suspend fun observeProfile(): Flow<ProfileEntity>
}