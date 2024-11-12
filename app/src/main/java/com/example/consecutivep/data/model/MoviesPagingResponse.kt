package com.example.consecutivep.data.model
import androidx.annotation.Keep

@Keep
data class MoviesPagingResponse(
    val docs: List<MovieResponse?>?,
    val total: Int?,
    val limit: Int?,
    val page: Int?,
    val pages: Int?
)