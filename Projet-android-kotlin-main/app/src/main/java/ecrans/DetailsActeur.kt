import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.monapp.MainViewModel

@Composable
fun ActorDetailsScreen(viewModel: MainViewModel, actorId: Int) {
    val actorDetails by viewModel.actorDetails.collectAsState()

    LaunchedEffect(actorId) {
        viewModel.getActorDetails(actorId)
    }

    actorDetails?.let { actor ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            val imageUrl = viewModel.getImageUrl(actor.profile_path)
            AsyncImage(
                model = imageUrl,
                contentDescription = actor.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Text(text = actor.name, style = MaterialTheme.typography.headlineMedium)
            Text(text = "Popularité: ${actor.popularity}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Département: ${actor.known_for_department}", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))

            actor.knownFor?.forEach { knownFor ->
                Text(text = knownFor.title, style = MaterialTheme.typography.bodySmall)
            }
        }
    } ?: run {
        Text("Chargement des détails de l'acteur...", modifier = Modifier.padding(16.dp))
    }
}
