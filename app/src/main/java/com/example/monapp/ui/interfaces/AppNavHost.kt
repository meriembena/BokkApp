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
import com.example.monapp.ui.theme.Purple80
import com.example.monapp.viewmodel.MainViewModel
import view.HomeScreen
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlinx.coroutines.delay

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
        composable("search") {
            SearchBooksScreen(viewModel,navController)
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
    var showFilters by remember { mutableStateOf(false) }
    var titleFilter by remember { mutableStateOf("") }
    var authorFilter by remember { mutableStateOf("") }
    var applyFilter by remember { mutableStateOf(false) }
    var showMessage by remember { mutableStateOf(true) } // état pour le message

    LaunchedEffect(query) {
        viewModel.searchBooks(query)
        // Message affiché 1.5 secondes puis disparaît
        showMessage = true
        delay(3500)
        showMessage = false
    }

    val filteredBooks = remember(applyFilter, titleFilter, authorFilter, books) {
        if (!applyFilter) books
        else books.filter { book ->
            val titleMatch = book.title?.contains(titleFilter, ignoreCase = true) ?: false
            val authorMatch = book.authorName?.any { it.contains(authorFilter, ignoreCase = true) } ?: false
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
        // Bouton pour afficher/cacher les filtres
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

        // Affichage du message temporaire
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
