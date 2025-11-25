package com.example.monapp.ui.interfaces

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(
    navController: NavHostController,
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()

    ModalDrawerSheet {

        Text(
            "Menu",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Accueil") },
            selected = false,
            onClick = {
                scope.launch {
                    drawerState.close()
                }
                navController.navigate("home")
            }
        )

        NavigationDrawerItem(
            icon = { Icon(Icons.Default.List, contentDescription = null) },
            label = { Text("Liste des livres") },
            selected = false,
            onClick = {
                scope.launch {
                    drawerState.close()
                }
                navController.navigate("books")
            }
        )

        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
            label = { Text("Réglages") },
            selected = false,
            onClick = {
                scope.launch {
                    drawerState.close()
                }
                navController.navigate("settings")
            }
        )

        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Info, contentDescription = null) },
            label = { Text("À propos de l'app") },
            selected = false,
            onClick = {
                scope.launch {
                    drawerState.close()
                }
                navController.navigate("about")
            }
        )
    }
}