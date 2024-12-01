package com.example.consecutivep.components

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AudioPlayerViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext

    // Состояние аудиоплеера
    private val _isAudioPlaying = MutableStateFlow(false)
    val isAudioPlaying: StateFlow<Boolean> = _isAudioPlaying

    // Экземпляр аудиоплеера
    val audioPlayer: ExoPlayer = ExoPlayer.Builder(context).build()

    init {
        // Инициализация аудио
        viewModelScope.launch {
            val audioItem = MediaItem.fromUri("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
            audioPlayer.setMediaItem(audioItem)
            audioPlayer.prepare()
        }
    }

    fun toggleAudioPlayPause() {
        if (audioPlayer.isPlaying) {
            audioPlayer.pause()
            _isAudioPlaying.value = false
        } else {
            audioPlayer.play()
            _isAudioPlaying.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayer.release()
        audioPlayer.release()
    }
}
