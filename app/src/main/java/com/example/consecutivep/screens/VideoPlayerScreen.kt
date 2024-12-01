package com.example.consecutivep.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.activity.ComponentActivity
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.ui.PlayerView
import com.example.consecutivep.components.AudioPlayerViewModel
import com.example.consecutivep.components.VideoPlayerViewModel


@Composable
fun VideoPlayerScreen(viewModel: VideoPlayerViewModel = viewModel(),
                      audioViewModel: AudioPlayerViewModel = viewModel()) {
    val isPlaying by viewModel.isPlaying.collectAsState()
    val isFullScreen by viewModel.isFullScreen.collectAsState()
    val playerError by viewModel.playerError.collectAsState()
    val isAudioPlaying by audioViewModel.isAudioPlaying.collectAsState()
    val context = LocalContext.current // Получаем контекст из Compose
    val activity = context as? ComponentActivity

    // Управляем полноэкранным режимом
    LaunchedEffect(isFullScreen) {
        activity?.let {
            val window = it.window
            val insetsController = WindowInsetsControllerCompat(window, window.decorView)
            if (isFullScreen) {
                insetsController.hide(WindowInsetsCompat.Type.systemBars())
            } else {
                insetsController.show(WindowInsetsCompat.Type.systemBars())
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Отображение ExoPlayer
        Box(
            modifier = if (isFullScreen) Modifier.fillMaxSize() else Modifier.aspectRatio(16 / 9f)
        ) {
            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        player = viewModel.exoPlayer
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        // Сообщение об ошибке
        playerError?.let { error ->
            Text(
                text = "Error: $error",
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }

        // Кнопки управления
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Button(onClick = { viewModel.togglePlayPause() }) {
                Text(if (isPlaying) "Pause" else "Play")
            }

            Button(onClick = { viewModel.toggleFullScreen() }) {
                Text(if (isFullScreen) "Exit Fullscreen" else "Fullscreen")
            }
        }
        Text(
            text = "Audio Player",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(8.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Button(onClick = { audioViewModel.toggleAudioPlayPause() }) {
                Text(if (isAudioPlaying) "Pause Audio" else "Play Audio")
            }
        }
    }
}
