package com.example.consecutivepracts.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.consecutivepracts.components.MovieViewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.consecutivep.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable fun MovieDetailScreen(movieId: Int, navController: NavController, viewModel: MovieViewModel = viewModel()) {
    val movie = viewModel.movies.find { it.id == movieId }

    if (movie != null) {
        Column(
            modifier = Modifier.padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = rememberImagePainter(movie.posterUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(400.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = movie.title,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = movie.premiere.takeLast(5).replace("г", "") + ", "
                        + movie.genre,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = movie.country + ", " + movie.duration,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Больше о фильме \"${movie.title}\"", fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = movie.description,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Режиссер: " + movie.director, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "В ролях: ", style = MaterialTheme.typography.bodyLarge,)
            val actorList: List<String> = movie.starring.split(", ")
            LazyRow {
                items(actorList) { actor ->
                    Text(
                        actor, style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(8.dp)
                            .border(
                                2.dp,
                                color = colorResource(R.color.pink),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(vertical = 6.dp, horizontal = 10.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    } else {
        Text(text = "Фильм не найден", style = MaterialTheme.typography.bodyMedium)
    }
}




