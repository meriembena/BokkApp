package ecrans

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.monapp.MainViewModel

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*

@Composable
fun SeriesDetailsScreen(viewModel: MainViewModel, seriesId: Int) {
    val serieDetails by viewModel.serieDetails.collectAsState()
    LaunchedEffect(seriesId) {
        if (serieDetails == null) {
            viewModel.getSeriesDetails(seriesId)
        }
    }
    serieDetails?.let { details ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            val imageUrl = viewModel.getImageUrl(details.backdrop_path)
            AsyncImage(
                model = imageUrl,
                contentDescription = details.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Text(text = details.name, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = details.overview, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(8.dp))
            if (details.genres.isNotEmpty()) {
                Text(
                    text = details.genres.joinToString(", ") { it.name },
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            } else {
                Text(text = "Aucun genre disponible", style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "⭐ ${details.vote_average} / 10", style = MaterialTheme.typography.bodyMedium)
            Text(text = "${details.vote_count} votes", style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(8.dp))
            if (details.seasons.isNotEmpty()) {
                details.seasons.forEach { season ->
                    Text(
                        text = "Saison ${season.season_number}: ${season.name}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            } else {
                Text(text = "Aucune saison disponible", style = MaterialTheme.typography.bodySmall)
            }
        }
    } ?: run {
        Text("Chargement des détails...", modifier = Modifier.padding(16.dp))
    }
}