package com.example.monapp.ui.interfaces

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.monapp.models.Book
import com.example.monapp.ui.theme.Purple80
import com.example.monapp.viewmodel.MainViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SearchBooksScreen(viewModel: MainViewModel, navController: NavHostController) {
    var query by remember { mutableStateOf("") }
    var searchTriggered by remember { mutableStateOf(false) }
    val books by viewModel.books.collectAsState()
    val filteredBooks = remember(searchTriggered, query, books) {
        if (!searchTriggered || query.isEmpty()) emptyList()
        else books.filter { book ->
            book.title.contains(query, ignoreCase = true) ||
                    book.authorName.any { it.contains(query, ignoreCase = true) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                searchTriggered = false
            },
            label = { Text("Rechercher un livre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { searchTriggered = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Purple80)
        ) {
            Text("Rechercher")
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filteredBooks) { book ->
                BookItem(book = book, navController = navController)
            }
        }
    }
}

@Composable
fun BookItem(book: Book, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                val encodedTitle = URLEncoder.encode(book.title, StandardCharsets.UTF_8.toString())
                navController.navigate("bookDetails/$encodedTitle")
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = book.title, style = MaterialTheme.typography.titleMedium)
            Text(
                text = "Auteur(s): ${book.authorName.joinToString(", ")}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}