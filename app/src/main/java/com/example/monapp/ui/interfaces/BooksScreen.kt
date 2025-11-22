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
import androidx.compose.ui.unit.sp
import com.example.monapp.viewmodel.MainViewModel

@Composable
fun BooksScreen(viewModel: MainViewModel) {
    val books by viewModel.books.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.searchBooks("harry potter")
    }

    val background = Brush.verticalGradient(
        colors = listOf(Color(0xFFB3E5FC), Color(0xFFE1F5FE))
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(8.dp)
    ) {
        items(books) { book ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = book.title ?: "Titre inconnu",
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                            color = Color(0xFF0288D1)
                        )

                        val authors = book.authorName?.joinToString(", ") ?: "Auteur inconnu"
                        Text(
                            text = "Auteur(s) : $authors",
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                            color = Color.Gray
                        )
                    }



                }
            }
        }
    }
}

