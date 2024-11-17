package com.example.consecutivep.presentation.pagingSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.consecutivep.domain.IMovieRepository
import com.example.consecutivep.presentation.model.MovieUiModel
import com.example.consecutivep.presentation.mapper.MovieUiMapper
import java.io.IOException
import java.net.SocketTimeoutException

class MoviePagingSource(
    private val repository: IMovieRepository,
    private val uiMapper: MovieUiMapper,
    private val type: String,
    private val contentStatus: String
) : PagingSource<Int, MovieUiModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieUiModel> {
        val currentPage = params.key ?: 1
        Log.d("MoviePagingSource", "Loading page: $currentPage with page size: ${params.loadSize}")
        return try {
            val movies = repository.getMovie(
                type = type,
                contentStatus = contentStatus,
                page = currentPage,
                pageSize = params.loadSize
            )
            Log.d("MoviePagingSource", "Loaded ${movies.size} items")

            LoadResult.Page(
                data = movies.map { uiMapper.mapMovie(it) },
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (movies.isEmpty()) null else currentPage + 1
            )


        } catch (e: SocketTimeoutException) {
            LoadResult.Error(IOException("Server took too long to respond. Please try again."))
        }
        catch (e: Exception) {
            Log.e("MoviePagingSource", "Error loading data", e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieUiModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
