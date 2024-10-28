package com.example.consecutivep.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MovieResponse(
    val id: Long?,
    val name: String?,
    val description: String?,
    val year: Int?,
    val rating: RatingResponse?,
    @SerializedName("movieLength") val movieLength: Int?,
    val genres: List<GenreResponse?>?,
    val countries: List<CountryResponse?>?,
    val poster: PosterResponse?,
    @SerializedName("persons") val persons: List<PersonResponse?>?
)