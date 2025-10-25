package edu.pe.cibertec.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.pe.cibertec.api.RetrofitInstance
import edu.pe.cibertec.model.Podcast
import edu.pe.cibertec.player.MediaPlayerManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception


class PodcastViewModel : ViewModel(){

    private val _podcasts = MutableStateFlow<List<Podcast>>(emptyList())
    val podcasts : StateFlow<List<Podcast>> = _podcasts

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    // Agregar controlers
    private val mediaPlayerManager = MediaPlayerManager()

    private val _currentPlayingUrl = MutableStateFlow<String?>(null)
    val currentPlayingUrl : StateFlow<String?> = _currentPlayingUrl

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying



    fun playPodCast(url: String? ){
        url?.let{
            mediaPlayerManager.play(it)
            _currentPlayingUrl.value = it
            _isPlaying.value = true
        }
    }
    fun pausePodCast(){
        mediaPlayerManager.pause()
        _isPlaying.value = false
    }
    fun stopPodcast(){
        mediaPlayerManager.stop()
        _currentPlayingUrl.value = null
        _isPlaying.value = false
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayerManager.relese()
    }

    init {
        fetchPodcasts()
    }

    private fun fetchPodcasts(){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.searchPodcast()
                _podcasts.value = response.results
                response.results.forEach { podcast ->
                    println("trackName: ${podcast.trackName} ")
                    println("artworkUrl: ${podcast.artWorkl100} ")
                    println("previewURL: ${podcast.previewUrl} ")
                }
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _isLoading.value = false
            }
        }
    }


}