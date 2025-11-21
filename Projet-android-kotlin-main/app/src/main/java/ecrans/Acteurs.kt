package ecrans
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.monapp.MainViewModel
import com.example.monapp.R
import com.example.monapp.models.ActeurModel

@Composable
fun ActorsScreen(viewModel: MainViewModel, navController: NavController) {
    val actors by viewModel.acteurs.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.lastActors()
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(actors) { actor ->
            ActorItem(actor) {
                navController.navigate("actorDetails/${actor.id}")
            }
        }
    }
}

@Composable
fun ActorItem(actor: ActeurModel, onClick: () -> Unit) {
    val imageUrl = "https://image.tmdb.org/t/p/w300${actor.profile_path}"
    val placeholderImage = painterResource(id = R.drawable.placeholder)

    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 160.dp, height = 240.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = actor.name,
                error = placeholderImage,
                fallback = placeholderImage,
                modifier = Modifier
                    .size(128.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .shadow(elevation = 4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = actor.name,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = actor.known_for_department ?: "Information non disponible",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}
