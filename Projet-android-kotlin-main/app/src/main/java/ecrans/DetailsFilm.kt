package ecrans

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.monapp.MainViewModel

@Composable
fun MovieDetailsScreen(viewModel: MainViewModel, movieId: Int) {
    LaunchedEffect(movieId) {
        viewModel.getMovieDetails(movieId)
    }
    val movie = viewModel.movieDetails.collectAsState().value

    movie?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = viewModel.getImageUrl(it.posterPath),
                contentDescription = it.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = it.title,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "⭐ ${it.voteAverage} / 10",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFFFD700)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it.overview ?: "Pas de description disponible.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}