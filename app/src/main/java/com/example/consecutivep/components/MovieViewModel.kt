package com.example.consecutivep.components

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.consecutivep.datastore.DataStoreManager
import com.example.consecutivep.domain.IMovieRepository
import com.example.consecutivep.state.ListState
import com.example.consecutivep.utils.LocalUtils.isFilter
import com.example.consecutivepracts.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.UnknownHostException
import java.util.logging.Logger


class MovieViewModel(
    context: Context,
    private val repository: IMovieRepository
) : ViewModel() {
    private val mutableState = MutableListState()
    val viewState = mutableState as ListState
    val dataStoreManager = DataStoreManager(context)
    init {
        loadTmp()
    }

    fun loadTmp() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dataStoreManager.getSettings().collect { settings ->

                    val savedStatus = settings.type.split(",").filter { it.isNotBlank() }
                    val savedContentRating =
                        settings.status.split(",").filter { it.isNotBlank() }

                    isFilter.value =
                        savedStatus.isNotEmpty() || savedContentRating.isNotEmpty()
                    if (isFilter.value) {
                        loadFilms(savedStatus[0], savedContentRating[0])
                    }else{
                        loadFilms(mutableState.searchName, mutableState.filterContentStatus )
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    private fun loadFilms(type: String, contentStatus: String) {
        LOG.info("loadFilms, $type, $contentStatus")

        viewModelScope.launch {
            try {
                mutableState.loading = true
                mutableState.error = null
                mutableState.items = emptyList()
                mutableState.items = repository.getMovie(type, contentStatus)

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
        override var searchName: String by mutableStateOf(DEFAULT_SEARCH_NAME)
        override var filterContentStatus: String by mutableStateOf(DEFAULT_CONTENT_STATUS)
        override var items: List<Movie> by mutableStateOf(emptyList())
        override var error: String? by mutableStateOf(null)
        override var loading: Boolean by mutableStateOf(false)
    }


    companion object {
        private const val DEFAULT_CONTENT_STATUS = "completed"
        private const val DEFAULT_SEARCH_NAME = "movie"
        private val LOG = Logger.getLogger(MovieViewModel::class.java.name)
    }

}




