package com.example.consecutivepracts.components


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
import ru.urfu.consecutivepractice.coroutinesUtils.launchLoadingAndError
import java.io.IOException
import java.util.logging.Logger

//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("dataStore_settings")

class MovieViewModel(
    val context: Context,
    private val repository: IMovieRepository
) : ViewModel() {
    private val mutableState = MutableListState()
    val viewState = mutableState as ListState
    val dataStoreManager = DataStoreManager(context)
    init {
        loadTmp()
    }
    private fun loadTmp() {
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
        viewModelScope.launchLoadingAndError(
            handleError = { error ->
                mutableState.error = when (error) {
                    is IOException -> "Проверьте подключение к интернету."
                    else -> error.localizedMessage
                }},
            updateLoading = { mutableState.loading = it }
        ) {
            mutableState.error = null
            mutableState.items = repository.getMovie(type, contentStatus )
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




