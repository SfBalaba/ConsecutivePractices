package com.example.consecutivep.components

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.consecutivep.datastore.DataStoreManager
import com.example.consecutivep.domain.IMovieRepository
import com.example.consecutivep.presentation.mapper.MovieUiMapper
import com.example.consecutivep.presentation.model.MovieUiModel
import com.example.consecutivep.presentation.pagingSource.MoviePagingSource
import com.example.consecutivep.state.ListState
import com.example.consecutivep.utils.LocalUtils.isFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MovieViewModel(
    private val repository: IMovieRepository,
    private val uiMapper: MovieUiMapper,
    private val dataStoreManager : DataStoreManager
) : ViewModel() {

    private val mutableState = MutableListState()
    val viewState = mutableState as ListState


    private val _filterParams = MutableStateFlow(FilterParams())
    val filterParams = _filterParams



    val pagedMovies: Flow<PagingData<MovieUiModel>> =
        filterParams.flatMapLatest { params ->
            Pager(
                config = PagingConfig(
                    pageSize = 20,
                    prefetchDistance = 5,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = {
                    MoviePagingSource(
                        repository = repository,
                        uiMapper = uiMapper,
                        type = params.type,
                        contentStatus = params.contentStatus
                    )
                }
            ).flow.cachedIn(viewModelScope)
        }

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
                        updateFilters(savedStatus.getOrNull(0) ?: "", savedContentRating.getOrNull(0) ?: "")
                    }
                }
            } catch (e: Exception) {
                Log.d("MovieViewModel", "${e.message}")
            }
        }
    }

    // Обновление фильтров
    fun updateFilters(type: String, contentStatus: String) {
        _filterParams.value = FilterParams(type, contentStatus)
    }

    companion object {
        private const val DEFAULT_CONTENT_STATUS = "completed"
        private const val DEFAULT_SEARCH_NAME = "movie"
    }

    // Модель для фильтров
    data class FilterParams(
        val type: String = DEFAULT_SEARCH_NAME,
        val contentStatus: String = DEFAULT_CONTENT_STATUS
    )

    private class MutableListState : ListState {
        override var items: List<MovieUiModel> by mutableStateOf(emptyList())
        override var searchName: String by mutableStateOf(DEFAULT_SEARCH_NAME)
        override var filterContentStatus: String by mutableStateOf(DEFAULT_CONTENT_STATUS)
        override var error: String? by mutableStateOf(null)
        override var loading: Boolean by mutableStateOf(false)
    }
}
