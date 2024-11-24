package com.example.consecutivep.domain.repository

import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalTime
import com.example.consecutivep.domain.model.ProfileEntity

interface IProfileRepository {
    suspend fun getProfile(): ProfileEntity?

    suspend fun setProfile(
        photoUri: String,
        name: String,
        url: String,
        time: LocalTime,
    ): ProfileEntity

    suspend fun observeProfile(): Flow<ProfileEntity>
}