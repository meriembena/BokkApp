import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.monapp.R

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar(
        containerColor = Color(0xFF0288D1),
        contentColor = Color.White,
        modifier = Modifier.height(60.dp)
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(R.drawable.icon),
                    contentDescription = "Livres",
                    modifier = Modifier.size(28.dp) // Taille moyenne
                )
            },
            label = { Text("Livres") },
            selected = navController.currentDestination?.route?.startsWith("books") == true,
            onClick = { navController.navigate("books") }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(R.drawable.recherche),
                    contentDescription = "Recherche",
                    modifier = Modifier.size(28.dp)
                )
            },
            label = { Text("Recherche") },
            selected = navController.currentDestination?.route == "search",
            onClick = { navController.navigate("search") }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(R.drawable.favoris),
                    contentDescription = "Favoris",
                    modifier = Modifier.size(28.dp)
                )
            },
            label = { Text("Favoris") },
            selected = navController.currentDestination?.route == "favoris",
            onClick = { navController.navigate("favoris") }
        )
    }
}

