package com.example.monapp.ui.interfaces

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun DrawerContent(navController: NavHostController) {

    ModalDrawerSheet {

        Text(
            "Menu",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        NavigationDrawerItem(
            label = { Text("Accueil") },
            selected = false,
            onClick = { navController.navigate("home") }
        )

        NavigationDrawerItem(
            label = { Text("Liste des livres") },
            selected = false,
            onClick = { navController.navigate("books") }
        )

        NavigationDrawerItem(
            label = { Text("À propos de l’app") },
            selected = false,
            onClick = { navController.navigate("about") }
        )
    }
}
