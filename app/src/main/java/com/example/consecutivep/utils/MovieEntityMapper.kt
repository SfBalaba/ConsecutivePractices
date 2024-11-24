package com.example.consecutivep.utils

import com.example.consecutivep.model.MovieEntity
import com.example.consecutivep.presentation.model.MovieUiModel

object MovieEntityMapper {
        fun toEntity(movieUiModel: MovieUiModel): MovieEntity {
        return MovieEntity(
            id = movieUiModel.id.toString(),
            title = movieUiModel.title,
            genres = movieUiModel.genre,
            imageUrl = movieUiModel.posterUrl,
            year = movieUiModel.premiere.replace(" г.", "").toInt(),
            country = movieUiModel.countries,
            description = movieUiModel.description
        )
    }
}
