package ecrans

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.monapp.MainViewModel
import com.example.monapp.models.Movie

@Composable
fun FilmsScreen(viewModel: MainViewModel, onMovieClick: (Int) -> Unit) {
    val movies by viewModel.movies.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getTrendingMovies()
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(movies) { movie ->
            MovieItem(movie = movie, onMovieClick = onMovieClick, viewModel = viewModel)
        }
    }
}

@Composable
fun MovieItem(movie: Movie, onMovieClick: (Int) -> Unit, viewModel: MainViewModel) {
    val imageUrl = viewModel.getImageUrl(movie.poster_path)

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable { onMovieClick(movie.id) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            AsyncImage(
                model = imageUrl,
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.7f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 8.dp),
                maxLines = 1
            )
            Text(
                text = "⭐ ${movie.vote_average} / 10",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFFFD700),
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
