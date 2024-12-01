package com.example.consecutivep.components
import android.app.Application
import androidx.compose.runtime.remember
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.media3.common.PlaybackException
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideoPlayerViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext

    // Состояния UI
    private val _isPlaying = MutableStateFlow(true)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _isFullScreen = MutableStateFlow(false)
    val isFullScreen: StateFlow<Boolean> = _isFullScreen

    private val _playerError = MutableStateFlow<String?>(null)
    val playerError: StateFlow<String?> = _playerError

    // Экземпляр ExoPlayer
    val exoPlayer: ExoPlayer = ExoPlayer.Builder(context).build().apply {
        addListener(object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                _playerError.value = error.localizedMessage ?: "Playback Error"
            }
        })
    }



    init {
        // Инициализация видео
        viewModelScope.launch {
            var mediaItem = MediaItem.Builder()
                .setUri("https://www.w3schools.com/tags/mov_bbb.mp4")
                .setMimeType(MimeTypes.VIDEO_MP4) // Укажите MIME-тип
                .build()
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
        }
    }

    fun togglePlayPause() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
            _isPlaying.value = false
        } else {
            exoPlayer.play()
            _isPlaying.value = true
        }
    }

    fun toggleFullScreen() {
        _isFullScreen.value = !_isFullScreen.value
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer.release()
    }
}
