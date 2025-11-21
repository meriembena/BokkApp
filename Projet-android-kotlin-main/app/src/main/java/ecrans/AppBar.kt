package ecrans

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.monapp.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(viewModel: MainViewModel) {
    var searchQuery by remember { mutableStateOf("") }

    androidx.compose.material.TopAppBar(
        title = {
            Text(text = "Fav'app")
        },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Rechercher...") },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(end = 8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent
                    )
                )
                IconButton(onClick = {
                    viewModel.setSearchQuery(searchQuery)
                    Log.d("Search", "Recherche pour : $searchQuery")
                }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
            }
        }
    )
}
