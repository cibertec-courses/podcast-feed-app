package edu.pe.cibertec.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import edu.pe.cibertec.model.Podcast
import edu.pe.cibertec.viewmodel.PodcastViewModel

@Composable
fun PodcastScreen(
    viewModel: PodcastViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val podcasts by viewModel.podcasts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val currentPlayingUrl by viewModel.currentPlayingUrl.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(podcasts) { podcast ->
                    PodcastItem(
                        podcast = podcast,
                        isPlaying = isPlaying,
                        isCurrentPodcast = podcast.previewUrl == currentPlayingUrl,
                        onPlayClick = { viewModel.playPodcast(podcast.previewUrl) },
                        onPauseClick = { viewModel.pausePodcast() },
                        onStopClick = { viewModel.stopPodcast() }
                    )
                }
            }
        }
    }
}

@Composable
fun PodcastItem(
    podcast: Podcast,
    isPlaying: Boolean,
    isCurrentPodcast: Boolean,
    onPlayClick: () -> Unit,
    onPauseClick: () -> Unit,
    onStopClick: () -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Imagen del podcast
                AsyncImage(
                    model = podcast.artworkUrl100,
                    contentDescription = podcast.trackName,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(end = 16.dp)
                )

                // Título del podcast
                Text(
                    text = podcast.trackName ?: "Sin título",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
            }

            // Controles de reproducción
            if (podcast.previewUrl != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = onPlayClick, enabled = !isPlaying || !isCurrentPodcast) {
                        Text("Play")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = onPauseClick, enabled = isPlaying && isCurrentPodcast) {
                        Text("Pause")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = onStopClick, enabled = isCurrentPodcast) {
                        Text("Stop")
                    }
                }
            }
        }
    }
}