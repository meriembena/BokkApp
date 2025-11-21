// AppBar.kt
package com.example.monapp.ui.interfaces

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.monapp.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    viewModel: MainViewModel,
    navController: NavHostController,
    drawerState: DrawerState
) {
    var searchQuery by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    SmallTopAppBar(
        navigationIcon = {
            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
            }
        },
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "BookApp",
                    color = Color.White,
                    modifier = Modifier.clickable {
                        navController.navigate("home") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

                Spacer(modifier = Modifier.width(16.dp))

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Rechercher…") },
                    singleLine = true,
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Close, contentDescription = "Clear", tint = Color.Gray)
                            }
                        }
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White.copy(alpha = 0.15f),
                        cursorColor = Color.White,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.width(180.dp)
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    if (searchQuery.isNotBlank()) {
                        navController.navigate("books?query=$searchQuery")
                    }
                }
            ) {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color(0xFF0288D1)
        )
    )
}


