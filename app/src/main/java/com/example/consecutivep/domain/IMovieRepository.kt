package com.example.consecutivep.domain

import com.example.consecutivepracts.model.Movie

interface IMovieRepository {
        suspend fun getMovie(nameSearch: String): List<Movie>
}