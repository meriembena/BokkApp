package ecrans

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.monapp.MainViewModel
import com.example.monapp.models.SeriesModel

@Composable
fun SeriesScreen(viewModel: MainViewModel, onSeriesClick: (Int) -> Unit) {
    val series by viewModel.series.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.lastSeries()
    }
    Log.d("SeriesScreen", "Series: ${series.size}")
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(series) { serie ->
            SeriesItem(serie, onSeriesClick, viewModel)
        }
    }
}

@Composable
fun SeriesItem(serie: SeriesModel, onSeriesClick: (Int) -> Unit, viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onSeriesClick(serie.id) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageUrl = "https://image.tmdb.org/t/p/w300${serie.poster_path}"
        Card(
            modifier = Modifier
                .size(150.dp)
                .clip(MaterialTheme.shapes.medium)
                .shadow(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = serie.name,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = serie.name, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
        Text(text = serie.first_air_date, style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
        val genreNames = viewModel.getGenreNames(serie.genre_ids)
        Text(
            text = genreNames,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}
