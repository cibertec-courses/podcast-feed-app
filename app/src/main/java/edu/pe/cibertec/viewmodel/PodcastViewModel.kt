package edu.pe.cibertec.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.pe.cibertec.api.RetrofitInstance
import edu.pe.cibertec.model.Podcast
import edu.pe.cibertec.player.MediaPlayerManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PodcastViewModel : ViewModel() {

    private val _podcasts = MutableStateFlow<List<Podcast>>(emptyList())
    val podcasts: StateFlow<List<Podcast>> = _podcasts

    private val mediaPlayerManager = MediaPlayerManager()

    private val _currentPlayingUrl = MutableStateFlow<String?>(null)
    val currentPlayingUrl: StateFlow<String?> = _currentPlayingUrl

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    fun playPodcast(url: String?) {
        url?.let {
            mediaPlayerManager.play(it)
            _currentPlayingUrl.value = it
            _isPlaying.value = true
        }
    }

    fun pausePodcast() {
        mediaPlayerManager.pause()
        _isPlaying.value = false
    }

    fun stopPodcast() {
        mediaPlayerManager.stop()
        _currentPlayingUrl.value = null
        _isPlaying.value = false
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayerManager.release()
    }

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchPodcasts()
    }

    private fun fetchPodcasts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.searchPodcasts()
                println("JSON Response: $response")
                _podcasts.value = response.results

                // AGREGAR ESTAS LÍNEAS AQUÍ
                response.results.forEach { podcast ->
                    println("=== PODCAST COMPLETO ===")
                    println("trackName: ${podcast.trackName}")
                    println("artworkUrl100: ${podcast.artworkUrl100}")
                    println("previewUrl: ${podcast.previewUrl}")
                    println("========================")
                }

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }



}