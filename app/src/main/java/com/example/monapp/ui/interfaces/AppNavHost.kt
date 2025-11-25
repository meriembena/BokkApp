package com.example.monapp.ui.interfaces

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.monapp.viewmodel.MainViewModel
import com.example.monapp.viewmodel.SettingsViewModel
import view.HomeScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(),
    settingsViewModel: SettingsViewModel = viewModel()
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
            FavorisScreen(viewModel = viewModel, navController = navController)
        }
        composable("search") {
            SearchBooksScreen(viewModel, navController)
        }
        composable("settings") {
            SettingsScreen(navController, settingsViewModel)
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
            val favorites by viewModel.favorites.collectAsState()

            val book = books.find { it.title == decodedTitle }
                ?: favorites.find { it.title == decodedTitle }

            if (book != null) {
                BookDetailsScreen(
                    book = book,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Chargement du livre...")
                    }
                }
            }
        }
    }
}