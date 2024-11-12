package com.example.consecutivep.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.consecutivep.components.FavoriteViewModel
import com.example.consecutivep.ui.theme.components.LoadingScreen
import com.example.consecutivep.components.MovieViewModel
import com.example.consecutivep.presentation.model.MovieUiModel
import com.example.consecutivep.model.MovieEntity
import com.example.consecutivepracts.model.Movie


@Composable
fun MovieListScreen(viewModel: MovieViewModel, onMovieClick: (Long) -> Unit) {
    val state = viewModel.viewState
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        state.error?.let {
            Text(text = it)
            Spacer(modifier = Modifier.height(18.dp))
            Button(onClick = { viewModel.loadTmp() }) {
                Text("Попробовать снова")
            }
        }

        LazyColumn(
            Modifier.fillMaxSize(),
        ) {
            items(state.items) {
                ConstructorItem(movie = it, onMovieClick)
            }
        }
    }

    if (state.loading) {
        LoadingScreen()
    }
}


@Composable
private fun ConstructorItem(movie: MovieUiModel, onMovieClick: (Long) -> Unit) {

    val favoriteViewMovie: FavoriteViewModel = viewModel()
    val isFavorite = favoriteViewMovie.favoriteMovieList.any { it.id.toLong() == movie.id }

    ListItem(modifier = Modifier
        .clickable { onMovieClick(movie.id) }
        .padding(8.dp)
        .shadow(10.dp)
        .clip(
            RoundedCornerShape(10.dp)
        ), headlineContent = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(movie.posterUrl),
                contentDescription = null,
                modifier = Modifier.size(170.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(movie.title, style = MaterialTheme.typography.titleLarge)
                Text(
                    movie.description.take(80) + "...",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            IconButton(
                onClick = {
                    val movieEntity = MovieEntity(
                        id=movie.id.toString(),
                        title=movie.title,
                        genres = movie.genre.joinToString(", "),
                        imageUrl = movie.posterUrl,
                        year = movie.premiere,
                        country = movie.countries.joinToString(", "),
                        description = movie.description,
                    )
                    if (isFavorite) {
                        favoriteViewMovie.removeMovieFromFavorite(movieEntity)
                    } else {
                        favoriteViewMovie.addMovieToFavorite(movieEntity, movie.posterUrl)
                    }
                },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                    tint = if (isFavorite) Color.Red else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    })
}



