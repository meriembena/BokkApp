package com.example.monapp.ui.interfaces

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.monapp.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    settingsViewModel: SettingsViewModel
) {
    val darkTheme by settingsViewModel.darkTheme.collectAsState()
    val notificationsEnabled by settingsViewModel.notificationsEnabled.collectAsState()
    val autoUpdate by settingsViewModel.autoUpdate.collectAsState()

    val background = Brush.verticalGradient(
        colors = listOf(Color(0xFFB3E5FC), Color(0xFFE1F5FE))
    )

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Réglages", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Retour",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF0288D1)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(background)
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Section Apparence
            Text(
                text = "Apparence",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF0288D1),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Thème sombre
                    SettingSwitchItem(
                        title = "Thème sombre",
                        description = "Activer le mode sombre",
                        checked = darkTheme,
                        onCheckedChange = { settingsViewModel.setDarkTheme(it) }
                    )
                }
            }

            // Section Notifications
            Text(
                text = "Notifications",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF0288D1),
                modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SettingSwitchItem(
                        title = "Notifications",
                        description = "Recevoir des notifications pour les nouveaux livres",
                        checked = notificationsEnabled,
                        onCheckedChange = { settingsViewModel.setNotificationsEnabled(it) }
                    )
                }
            }

            // Section Données
            Text(
                text = "Données",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF0288D1),
                modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SettingSwitchItem(
                        title = "Mise à jour automatique",
                        description = "Actualiser les livres automatiquement",
                        checked = autoUpdate,
                        onCheckedChange = { settingsViewModel.setAutoUpdate(it) }
                    )
                }
            }
            Text(
                text = "Application",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF0288D1),
                modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Version",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.DarkGray
                    )
                    Text(
                        text = "1.0.0",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Développé par",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.DarkGray
                    )
                    Text(
                        text = "Meriam Ben Ali",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun SettingSwitchItem(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                color = Color(0xFF0288D1)
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color(0xFF0288D1),
                checkedTrackColor = Color(0xFF81D4FA)
            )
        )
    }
}