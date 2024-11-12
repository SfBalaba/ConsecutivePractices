package com.example.consecutivepracts.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Long,
    val title: String,
    val description: String,
    val posterUrl: String,
    val premiere: Int,
    val type: String,
    val countries: List<String>,
    val genre: List<String>,
    val director: List<String>,
    val starring: List<String>,
    val duration: Int,
    val rating: Number,
    ) : Parcelable
