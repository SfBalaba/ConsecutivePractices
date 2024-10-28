package com.example.consecutivep.data.repository

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
        override suspend fun getMovie(nameSearch: String): List<Movie> {
            return withContext(Dispatchers.IO) {
                mapper.mapMovie(api.getMovies("YJKWE1C-8WSMSY3-PB0XDGX-2JN8N8N",1, 10,
                    selectFields = listOf("id", "name", "description", "year", "rating",
                        "movieLength", "genres", "countries", "poster", "persons"),
                    notNullFields= listOf("id", "name", "description", "year", "movieLength", "poster.url", "persons.name"),
                    sortField = listOf("rating.imdb"),
                    sortType="1",
                    type= listOf(nameSearch),
//                    status= listOf()
                ))
            }
        }
    }
