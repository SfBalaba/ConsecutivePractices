package com.example.consecutivep.components

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.consecutivep.domain.IMovieRepository
import com.example.consecutivep.state.ListState
import com.example.consecutivepracts.model.Movie
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.UnknownHostException


class MovieViewModel(
    private val repository: IMovieRepository,
) : ViewModel() {
    private val mutableState = MutableListState()
    val viewState = mutableState as ListState

    init {
        loadFilms()
    }

    fun loadFilms() {
        viewModelScope.launch {
            try {
                mutableState.loading = true
                mutableState.error = null

                mutableState.items = emptyList()
                mutableState.items = repository.getMovie(viewState.searchName)

            } catch (e: IOException) {
                mutableState.error =
                    "Проблемы с подключением к интернету. Проверьте ваше подключение."

            } catch (e: UnknownHostException) {
                mutableState.error = "Не удается найти сервер. Проверьте ваше подключение."

            } catch (e: Exception) {
                mutableState.error = "Произошла ошибка: ${e.localizedMessage}"

            } finally {
                mutableState.loading = false
            }
        }
    }


    private class MutableListState : ListState {
        override var searchName: String by mutableStateOf("movie")
        override var items: List<Movie> by mutableStateOf(emptyList())
        override var error: String? by mutableStateOf(null)
        override var loading: Boolean by mutableStateOf(false)
    }

}







