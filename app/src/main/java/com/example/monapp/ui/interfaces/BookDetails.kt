package com.example.monapp.ui.interfaces

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.monapp.models.Book
import com.example.monapp.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun BookDetailsScreen(
    book: Book,
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    val background = Brush.verticalGradient(
        colors = listOf(Color(0xFFB3E5FC), Color(0xFFE1F5FE))
    )

    val favorites by viewModel.favorites.collectAsState()
    val isFavorite = favorites.contains(book)
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SnackbarHost(hostState = snackbarHostState)

        TextButton(onClick = onBack) {
            Text("← Retour", color = Color(0xFF0288D1), fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                book.coverUrl?.let { url ->
                    Image(
                        painter = rememberAsyncImagePainter(url),
                        contentDescription = "Couverture du livre",
                        modifier = Modifier
                            .height(220.dp)
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                }
                Text(
                    text = book.title.ifEmpty { "Titre inconnu" },
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 28.sp,
                        color = Color(0xFF0288D1)
                    )
                )

                val authors = book.authorName.joinToString(", ").ifEmpty { "Auteur inconnu" }
                Text(
                    text = "Auteur(s) : $authors",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                    color = Color(0xFF555555)
                )
                book.firstPublishYear?.let { year ->
                    Text(
                        text = "Année de publication : $year",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                        color = Color(0xFF777777)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = Color(0xFFB0BEC5), thickness = 1.dp)
                Spacer(modifier = Modifier.height(16.dp))
                book.description?.let { desc ->
                    Text(
                        text = desc,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                        color = Color(0xFF555555)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        if (isFavorite) {
                            viewModel.removeFromFavorites(book)
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Livre retiré des favoris")
                            }
                        } else {
                            viewModel.addToFavorites(book)
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Livre ajouté aux favoris")
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isFavorite) Color.Gray else Color(0xFF0288D1)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = if (isFavorite) "Retirer des favoris" else "Ajouter aux favoris",
                        color = Color.White
                    )
                }
            }
        }
    }
}
