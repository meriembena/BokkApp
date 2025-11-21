package ecrans

import ActorDetailsScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.monapp.MainViewModel
import com.example.monapp.models.MovieHorror

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    val viewModel: MainViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") { HomeScreen(navController) }
        composable("films") {
            FilmsScreen(viewModel = viewModel, onMovieClick = { movieId ->
                navController.navigate("movieDetails/$movieId")
            })
        }
        composable("series") {
            SeriesScreen(viewModel = viewModel, onSeriesClick = { seriesId ->
                navController.navigate("seriesDetails/$seriesId")
            })
        }
        composable("actors") { ActorsScreen(viewModel = viewModel, navController = navController) }
        composable("movieDetails/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toInt() ?: -1
            MovieDetailsScreen(viewModel, movieId)
        }
        composable("seriesDetails/{seriesId}") { backStackEntry ->
            val seriesId = backStackEntry.arguments?.getString("seriesId")?.toInt() ?: -1
            SeriesDetailsScreen(viewModel, seriesId)
        }
        composable("actorDetails/{actorId}") { backStackEntry ->
            val actorId = backStackEntry.arguments?.getString("actorId")?.toIntOrNull() ?: return@composable
            ActorDetailsScreen(viewModel, actorId)
        }
        composable("destination") {
            DestinationScreen(viewModel = viewModel)
        }

    }
}
