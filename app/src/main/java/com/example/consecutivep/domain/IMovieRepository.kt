package com.example.consecutivep.domain

import com.example.consecutivepracts.model.Movie

interface IMovieRepository {
        suspend fun getMovie(type: String, contentStatus: String, page: Int, pageSize: Int): List<Movie>
}