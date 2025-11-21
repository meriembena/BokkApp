package ecrans

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.monapp.R

@Composable
fun BottomNavigationBar(navController: androidx.navigation.NavHostController) {
    BottomNavigation {
        BottomNavigationItem(
            icon = { Icon(painterResource(id = R.drawable.movie ), contentDescription = "Films") },
            label = { Text("Films") },
            selected = navController.currentDestination?.route == "films",
            onClick = {
                navController.navigate("films") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        BottomNavigationItem(
            icon = { Icon(painterResource(id = R.drawable.tv), contentDescription = "Séries") },
            label = { Text("Séries") },
            selected = navController.currentDestination?.route == "series",
            onClick = {
                navController.navigate("series") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        BottomNavigationItem(
            icon = { Icon(painterResource(id = R.drawable.persons), contentDescription = "Acteurs") },
            label = { Text("Acteurs") },
            selected = navController.currentDestination?.route == "actors",
            onClick = {
                navController.navigate("actors") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        BottomNavigationItem(
            icon = { Icon(painterResource(id = R.drawable.bobine_film), contentDescription = "Destination") },
            label = { Text("Collections") },
            selected = navController.currentDestination?.route == "destination",
            onClick = {
                navController.navigate("destination") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}
