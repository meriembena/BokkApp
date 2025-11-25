package com.example.monapp.ui.interfaces

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.monapp.models.Book
import com.example.monapp.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun FavorisScreen(
    viewModel: MainViewModel,
    navController: NavHostController? = null
) {
    val favorites by viewModel.favorites.collectAsState()
    val favoritesCount by viewModel.favoritesCount.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val filteredFavorites = remember(searchQuery, favorites) {
        if (searchQuery.isEmpty()) {
            favorites
        } else {
            favorites.filter { book ->
                book.title.contains(searchQuery, ignoreCase = true) ||
                        book.authorName.any { it.contains(searchQuery, ignoreCase = true) }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mes Favoris ($favoritesCount)",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF0288D1)
                )
                if (favorites.isNotEmpty()) {
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Supprimer tout",
                            tint = Color.Red
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            if (favorites.isNotEmpty()) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Rechercher dans les favoris") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
            if (favorites.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Aucun livre en favoris",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Ajoutez des livres depuis la recherche !",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            } else if (filteredFavorites.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Aucun résultat pour \"$searchQuery\"",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredFavorites, key = { it.title }) { book ->
                        FavoriteBookCard(
                            book = book,
                            onRemove = {
                                viewModel.removeFromFavorites(book)
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "\"${book.title}\" retiré des favoris"
                                    )
                                }
                            },
                            onClick = {
                                navController?.let {
                                    val encodedTitle = URLEncoder.encode(
                                        book.title,
                                        StandardCharsets.UTF_8.toString()
                                    )
                                    it.navigate("bookDetails/$encodedTitle")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Supprimer tous les favoris ?") },
            text = { Text("Cette action est irréversible. Tous vos favoris seront supprimés.") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteAllFavorites()
                        showDeleteDialog = false
                        scope.launch {
                            snackbarHostState.showSnackbar("Tous les favoris ont été supprimés")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Supprimer")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Annuler")
                }
            }
        )
    }
}

@Composable
fun FavoriteBookCard(
    book: Book,
    onRemove: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            book.coverUrl?.let { url ->
                Image(
                    painter = rememberAsyncImagePainter(url),
                    contentDescription = "Couverture",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(end = 12.dp)
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                    color = Color(0xFF0288D1),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                val authors = book.authorName.joinToString(", ")
                if (authors.isNotEmpty()) {
                    Text(
                        text = authors,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                book.firstPublishYear?.let { year ->
                    Text(
                        text = "($year)",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
            IconButton(onClick = onRemove) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Retirer des favoris",
                    tint = Color.Red
                )
            }
        }
    }
}