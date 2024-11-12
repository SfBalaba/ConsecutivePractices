package com.example.consecutivep.components

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.consecutivep.domain.IMovieRepository
import com.example.consecutivep.presentation.mapper.MovieUiMapper
import com.example.consecutivep.presentation.model.MovieUiModel
import com.example.consecutivep.state.ListState
import com.example.consecutivepracts.model.Movie
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.UnknownHostException

class MovieViewModel(
    private val repository: IMovieRepository,
    private val uiMapper: MovieUiMapper,
) : ViewModel() {
    private val mutableState = MutableListState()
    val viewState = mutableState as ListState

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        mutableState.loading = false
        mutableState.error = when (exception) {
            is IOException -> "Проблемы с подключением к интернету. Проверьте ваше подключение."
            is UnknownHostException -> "Не удается найти сервер. Проверьте ваше подключение."
            else -> "Произошла ошибка: ${exception.localizedMessage}"
        }
    }

    init {
        loadFilms()
    }

    fun loadFilms() {
        viewModelScope.launch(exceptionHandler) {
            mutableState.loading = true
            mutableState.error = null
            mutableState.items = emptyList()
            val movies = repository.getMovie(viewState.searchName)
            mutableState.items = movies.map{uiMapper.mapMovie(it)}
            mutableState.loading = false
        }
    }

    private class MutableListState : ListState {
        override var searchName: String by mutableStateOf("movie")
        override var items: List<MovieUiModel> by mutableStateOf(emptyList())
        override var error: String? by mutableStateOf(null)
        override var loading: Boolean by mutableStateOf(false)
    }
}
