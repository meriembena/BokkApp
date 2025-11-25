package com.example.monapp.ui.interfaces

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.monapp.ui.theme.Purple80
import com.example.monapp.viewmodel.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun BooksScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    query: String
) {
    val books by viewModel.books.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    var showFilters by remember { mutableStateOf(false) }
    var titleFilter by remember { mutableStateOf("") }
    var authorFilter by remember { mutableStateOf("") }
    var applyFilter by remember { mutableStateOf(false) }
    var showMessage by remember { mutableStateOf(true) }

    LaunchedEffect(query) {
        viewModel.searchBooks(query)
        showMessage = true
        delay(3500)
        showMessage = false
    }

    val filteredBooks = remember(applyFilter, titleFilter, authorFilter, books) {
        if (!applyFilter) books
        else books.filter { book ->
            val titleMatch = book.title.contains(titleFilter, ignoreCase = true)
            val authorMatch = book.authorName.any { it.contains(authorFilter, ignoreCase = true) }
            (titleMatch || titleFilter.isEmpty()) && (authorMatch || authorFilter.isEmpty())
        }
    }

    val background = Brush.verticalGradient(
        colors = listOf(Color(0xFFB3E5FC), Color(0xFFE1F5FE))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(8.dp)
    ) {
        Button(
            onClick = { showFilters = !showFilters },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Purple80)
        ) {
            Text(if (showFilters) "Cacher les filtres" else "Filtrer")
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (showFilters) {
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = titleFilter,
                        onValueChange = { titleFilter = it },
                        label = { Text("Filtrer par titre") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = authorFilter,
                        onValueChange = { authorFilter = it },
                        label = { Text("Filtrer par auteur") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { applyFilter = true },
                        modifier = Modifier.align(Alignment.End),
                        colors = ButtonDefaults.buttonColors(containerColor = Purple80)
                    ) {
                        Text("Appliquer")
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = error ?: "Erreur inconnue",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            else -> {
                if (showMessage) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Patientez 1 seconde… la liste apparaît",
                            color = Color.DarkGray,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(filteredBooks) { book ->
                        BookCard(book = book, navController = navController)
                    }
                }
            }
        }
    }
}