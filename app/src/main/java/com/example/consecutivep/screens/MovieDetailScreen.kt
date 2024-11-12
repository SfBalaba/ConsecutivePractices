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
import coil.compose.rememberImagePainter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.consecutivep.R
import com.example.consecutivep.presentation.model.MovieUiModel
import com.example.consecutivepracts.model.Movie

@Composable
fun MovieDetailScreen(movie: MovieUiModel)
{
    Column(
        modifier = Modifier.padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = rememberImagePainter(movie.posterUrl),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(0.9f)
                .aspectRatio(1.0f)
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
            text = "${movie.premiere}, ${movie.genre}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "${movie.countries}, ${movie.duration}" ,
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
        if (!movie.director.isNullOrEmpty()){
            Text(
                text = "Режиссер: ${movie.director}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (movie.starring.size>0) {
            Text(text = "В ролях: ", style = MaterialTheme.typography.bodyLarge,)
            if (!movie.starring.isNullOrEmpty()) {
                LazyRow {
                    items(movie.starring) { actor ->
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
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}




