package com.example.consecutivep.screens


import android.graphics.BitmapFactory
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

import androidx.compose.material.icons.filled.Delete
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import com.example.consecutivep.components.FavoriteViewModel
import com.example.consecutivep.model.MovieEntity


@Composable
fun FavoritesScreen(navController: NavHostController) {

    val favoriteViewModel: FavoriteViewModel = viewModel()
    LaunchedEffect(key1 = true) {
        favoriteViewModel.loadFavoriteMovie()
    }

    val movieEntities = favoriteViewModel.favoriteMovieList

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Избранные фильмы",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn {
            items(movieEntities, key = { it.id }) { movie ->
                MovieItem(movie,
                    onClick = { navController.navigate("movie_detail/${movie.id}") },
                    onRemoveClick = { favoriteViewModel.removeMovieFromFavorite(movie) }
                )
            }
        }
    }
}


@Composable
fun MovieItem(
    movieEntity: MovieEntity,
    onClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    val title = movieEntity.title
    val imageUrl = movieEntity.imageUrl
    val genres = movieEntity.genres
    val year: Int = movieEntity.year
    val country: String = movieEntity.country
    val description: String = movieEntity.description

    val imageBytes = movieEntity.imageBytes
    val imageBitmap = imageBytes?.let {
        BitmapFactory.decodeByteArray(it, 0, it.size)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            if (imageBitmap != null) {
                Image(
                    bitmap = imageBitmap.asImageBitmap(),
                    contentDescription = title,
                    modifier = Modifier
                        .size(140.dp)
                        .padding(end = 16.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = "Default Image",
                    modifier = Modifier
                        .size(140.dp)
                        .padding(end = 16.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Год: $year", style = MaterialTheme.typography.bodySmall)
                Text(text = "Жанр: $genres", style = MaterialTheme.typography.bodySmall)
                Text(text = "Страна: $country", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(onClick = onRemoveClick,
                modifier = Modifier.size(36.dp)
                ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Remove from favorites",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

