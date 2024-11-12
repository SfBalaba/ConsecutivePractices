package com.example.consecutivep.state

import androidx.compose.runtime.Stable
import com.example.consecutivep.presentation.model.MovieUiModel
import com.example.consecutivepracts.model.Movie

@Stable
interface ListState {
    val searchName: String
    val items: List<MovieUiModel>
    val error: String?
    var loading: Boolean
}