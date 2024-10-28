package com.example.consecutivep.data.mapper

import com.example.consecutivep.data.model.MoviesPagingResponse
import com.example.consecutivepracts.model.Movie

class MovieResponseToEntityMapper {

        fun mapMovie(response: MoviesPagingResponse): List<Movie> {
            return response.docs?.map {
                Movie(
                    id = it?.id ?: 0L,
                    title = it?.name.orEmpty(),
                    posterUrl = it?.poster?.previewUrl.orEmpty(),
                    type = it?.name.orEmpty(),
                    premiere = it?.year ?: 0,
                    description = it?.description.orEmpty(),
                    countries = it?.countries.orEmpty().map {it?.name.toString() },
                    genre = it?.genres.orEmpty().map {it?.name.toString() },
                    director = it?.persons.orEmpty().filter { it?.profession == "режиссеры" }
                        .map { it?.name.orEmpty()},
                    starring = it?.persons.orEmpty().filter { it?.profession == "актеры"}
                        .map { it?.name.orEmpty()},
                    rating = it?.rating?.kp ?: 0.0,
                    duration = it?.movieLength ?: 0,
                )
            }.orEmpty()
        }
    }