package com.example.consecutivep.data.repository

import com.example.consecutivep.BuildConfig
import com.example.consecutivep.data.api.MovieApi
import com.example.consecutivep.data.mapper.MovieResponseToEntityMapper
import com.example.consecutivep.domain.IMovieRepository
import com.example.consecutivepracts.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val api: MovieApi,
                      private val mapper: MovieResponseToEntityMapper
) : IMovieRepository
        {
        override suspend fun getMovie(type: String, status: String, page: Int, pageSize: Int): List<Movie> {
            return withContext(Dispatchers.IO) {
                mapper.mapMovie(api.getMovies(
                    BuildConfig.MOVIE_API_KEY,page, pageSize,
                    selectFields = listOf("id", "name", "description", "year", "rating",
                        "movieLength", "genres", "countries", "poster", "persons"),
                    notNullFields= listOf("id", "name", "description",   "poster.url"),
                    sortField = listOf("rating.imdb"),
                    sortType="1",
                    type= listOf(type),
                    status= listOf(status),
                ))
            }
        }
    }
