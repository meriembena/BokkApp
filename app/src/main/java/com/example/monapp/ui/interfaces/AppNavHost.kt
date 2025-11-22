package com.example.monapp.ui.interfaces

import com.example.monapp.models.Book

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.monapp.viewmodel.MainViewModel
import view.HomeScreen
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            HomeScreen(navController)
        }
        composable("about") {
            AboutScreen(navController)
        }
        composable("favoris") {
            FavorisScreen(viewModel)
        }
        composable(
            route = "books?query={query}",
            arguments = listOf(navArgument("query") {
                type = NavType.StringType
                defaultValue = "science"
            })
        ) { backStackEntry ->
            val query = backStackEntry.arguments?.getString("query") ?: "science"
            BooksScreen(navController, viewModel, query)
        }

        composable(
            route = "bookDetails/{bookTitle}",
            arguments = listOf(navArgument("bookTitle") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookTitle = backStackEntry.arguments?.getString("bookTitle") ?: ""
            val decodedTitle = URLDecoder.decode(bookTitle, StandardCharsets.UTF_8.toString())

            val books by viewModel.books.collectAsState()
            val book = books.find { it.title == decodedTitle }

            if (book != null) {
                BookDetailsScreen(
                    book = book,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Chargement du livre...")
                }
            }
        }


    }
}

@Composable
fun BooksScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    query: String
) {
    val books by viewModel.books.collectAsState()
    var titleFilter by remember { mutableStateOf("") }
    var authorFilter by remember { mutableStateOf("") }

    LaunchedEffect(query) {
        viewModel.searchBooks(query)
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
        OutlinedTextField(
            value = titleFilter,
            onValueChange = { titleFilter = it },
            label = { Text("Filtrer par titre") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = authorFilter,
            onValueChange = { authorFilter = it },
            label = { Text("Filtrer par auteur") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        val filteredBooks = books.filter { book ->
            val titleMatch = book.title?.contains(titleFilter, ignoreCase = true) ?: false
            val authorMatch = book.authorName?.any { it.contains(authorFilter, ignoreCase = true) } ?: false
            (titleMatch || titleFilter.isEmpty()) && (authorMatch || authorFilter.isEmpty())
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredBooks) { book ->
                BookCard(book = book, navController = navController)
            }
        }
    }
}

@Composable
fun BookCard(book: Book, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable {
                val encodedTitle = URLEncoder.encode(book.title ?: "", StandardCharsets.UTF_8.toString())
                navController.navigate("bookDetails/$encodedTitle")
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = book.title ?: "Titre inconnu",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                color = Color(0xFF0288D1)
            )

            val authors = book.authorName?.joinToString(", ") ?: "Auteur inconnu"
            Text(
                text = "Auteur(s) : $authors",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )

            book.firstPublishYear?.let { year ->
                Text(
                    text = "Année : $year",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }


        }
    }
}

