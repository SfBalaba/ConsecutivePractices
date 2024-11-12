package com.example.consecutivep.presentation.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class MovieUiModel(
    val id: Long,
    val title: String,
    val description: String,
    val posterUrl: String,
    val premiere: String,
    val type: String,
    val countries: String,
    val genre: String,
    val director: String,
    val starring: List<String>,
    val duration: String,
    val rating: Number,
): Parcelable