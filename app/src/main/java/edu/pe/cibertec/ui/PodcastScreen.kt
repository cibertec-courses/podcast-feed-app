package edu.pe.cibertec.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
){
    val podcasts by viewModel.podcasts.collectAsState()
    val isLoading  by viewModel.isLoading.collectAsState()
    // controles reproduccion

    val currentPlayinUrl by viewModel.currentPlayingUrl.collectAsState()
    val isPlaying  by viewModel.isPlaying.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            )  {
                CircularProgressIndicator()
            }
        }else{
            LazyColumn (
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(podcasts) { podcast ->
                    PodcastItem(
                        podcast = podcast,
                        isPlaying = isPlaying,
                        isCurrentPodcast =  podcast.previewUrl == currentPlayinUrl,
                        onPlayClick =  { viewModel.playPodCast(podcast.previewUrl) },
                        onPauseClick = { viewModel.pausePodCast()},
                        onStopClick = { viewModel.stopPodcast()}
                    )
                }
            }
        }
    }
}

@Composable
fun PodcastItem (
    podcast: Podcast,
    isPlaying: Boolean,
    isCurrentPodcast: Boolean,
    onPlayClick:() -> Unit,
    onPauseClick:() -> Unit,
    onStopClick: () -> Unit

){
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            AsyncImage(
                model = podcast.artWorkl100,
                contentDescription = podcast.trackName,
                modifier = Modifier
                    .size(60.dp)
                    .padding(10.dp)
            )

            Text(
                text = podcast.trackName ?: "No title",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
        }
        // Controles reproduccion
        if (podcast.previewUrl != null){
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = onPlayClick,
                    enabled =  !isPlaying || !isCurrentPodcast
                ) {
                    Text("Play")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onPauseClick,
                    enabled =  !isPlaying && !isCurrentPodcast
                ) {
                    Text("Pause")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onStopClick,
                    enabled =  isCurrentPodcast
                ) {
                    Text("Stop")
                }

            }
        }
    }
}