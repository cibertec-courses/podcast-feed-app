package edu.pe.cibertec.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.pe.cibertec.model.Podcast
import edu.pe.cibertec.viewmodel.PodcastViewModel

@Composable
fun PodcastScreen(
    viewModel: PodcastViewModel,
    modifier: Modifier
){
    val podcasts by viewModel.podcasts.collectAsState()
    val isLoading  by viewModel.isLoading.collectAsState()

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
                    PodcastItem(podcast = podcast)
                }
            }
        }
    }
}

@Composable
fun PodcastItem (podcast: Podcast){
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
            Text(
                text = podcast.trackName ?: "No title",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}