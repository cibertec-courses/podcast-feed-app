package edu.pe.cibertec.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.pe.cibertec.api.RetrofitInstance
import edu.pe.cibertec.model.Podcast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception


class PodcastViewModel : ViewModel(){

    private val _podocasts = MutableStateFlow<List<Podcast>>(emptyList())
    val podcasts : StateFlow<List<Podcast>> = _podocasts

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    init {
        fetchPodcasts()
    }

    private fun fetchPodcasts(){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.searchPodcast()
                _podocasts.value = response.results
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _isLoading.value = false
            }
        }
    }


}