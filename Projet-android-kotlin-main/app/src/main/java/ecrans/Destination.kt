package ecrans

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.monapp.MainViewModel
import com.example.monapp.models.MovieHorror



@Composable
fun DestinationScreen(viewModel: MainViewModel) {
    val horrorCollections by viewModel.horrorCollections.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.searchHorrorCollections()
    }

    LazyColumn {
        itemsIndexed(horrorCollections) { index, collection ->
            if (index % 2 == 0) {
                CollectionItemLeftToRight(collection)
            } else {
                CollectionItemRightToLeft(collection)
            }
        }
    }
}



@Composable
fun CollectionItemLeftToRight(collection: MovieHorror) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = collection.name,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.weight(1f)
        )


        Image(
            painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${collection.poster_path}"),
            contentDescription = collection.name,
            modifier = Modifier.size(120.dp),
            contentScale = ContentScale.Crop
        )

    }
}

@Composable
fun CollectionItemRightToLeft(collection: MovieHorror) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${collection.poster_path}"),
            contentDescription = collection.name,
            modifier = Modifier.size(120.dp)
        )


        Text(
            text = collection.name,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.weight(1f)
        )
    }
}



