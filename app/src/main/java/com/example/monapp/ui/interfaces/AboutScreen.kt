package com.example.monapp.ui.interfaces

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.monapp.R
import com.example.monapp.ui.theme.Purple80

@Composable
fun AboutScreen(navController: NavHostController) {
    val background = Brush.verticalGradient(
        colors = listOf(Color(0xFFB3E5FC), Color(0xFFE1F5FE))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "À propos de BookApp",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 28.sp),
            color = Color(0xFF0288D1)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "BookApp est une application qui permet de rechercher et consulter des livres facilement. " +
                            "Vous pouvez parcourir les livres, voir les détails, gérer vos favoris et personnaliser l'expérience selon vos préférences.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.profil),
                    contentDescription = "Avatar développeuse",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(bottom = 12.dp)
                )

                Text(
                    text = "Développeuse : Meriam Ben Ali",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                    color = Color(0xFF0288D1)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Email : meriam@example.com",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Rôle : Développement et design de l'application",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Informations complémentaires",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                    color = Color(0xFF0288D1)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Version : 1.0.0", style = MaterialTheme.typography.bodyMedium, color = Color.DarkGray)
                Text("Contact support : support@bookapp.com", style = MaterialTheme.typography.bodyMedium, color = Color.DarkGray)
                Text("Site web : www.bookapp.com", style = MaterialTheme.typography.bodyMedium, color = Color.DarkGray)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Purple80)
        ) {
            Text("Retour")
        }
    }
}