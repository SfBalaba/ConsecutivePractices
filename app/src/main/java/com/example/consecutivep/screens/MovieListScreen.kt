package com.example.consecutivepracts.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.consecutivepracts.components.MovieViewModel


@Composable
fun MovieListScreen(onMovieClick: (Int) -> Unit) {
    val viewModel: MovieViewModel = viewModel()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(viewModel.movies) { movie ->
            ListItem(
                modifier = Modifier
                    .clickable { onMovieClick(movie.id) }
                    .padding(8.dp).shadow(10.dp).clip(
                    RoundedCornerShape(10.dp)),
                headlineContent = {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberImagePainter(movie.posterUrl),
                            contentDescription = null,
                            modifier = Modifier
                                .size(170.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(movie.title, style = MaterialTheme.typography.titleLarge)
                            Text(movie.description.take(100) + "...",
                                style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            )
        }
    }
}

