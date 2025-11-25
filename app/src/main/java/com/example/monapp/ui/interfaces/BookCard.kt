package com.example.monapp.ui.interfaces

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.monapp.models.Book
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun BookCard(book: Book, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable {
                val encodedTitle = URLEncoder.encode(book.title, StandardCharsets.UTF_8.toString())
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
                text = book.title,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                color = Color(0xFF0288D1)
            )

            val authors = book.authorName.joinToString(", ").ifEmpty { "Auteur inconnu" }
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