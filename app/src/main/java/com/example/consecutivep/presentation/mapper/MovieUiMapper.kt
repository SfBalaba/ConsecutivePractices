package com.example.consecutivep.presentation.mapper

import androidx.compose.ui.graphics.Color
import com.example.consecutivep.presentation.model.MovieUiModel
import com.example.consecutivepracts.model.Movie

class MovieUiMapper {

    fun mapMovie(entity: Movie): MovieUiModel {
        return MovieUiModel(
            entity.id,
            entity.title,
            entity.description,
            entity.posterUrl,
            "${entity.premiere} г.",
            entity.type,
            entity.countries.joinToString(", "),
            entity.genre.joinToString(", "),
            entity.director.joinToString(", "),
            entity.starring,
            "${entity.duration} мин.",
            entity.rating,
        )
    }
}