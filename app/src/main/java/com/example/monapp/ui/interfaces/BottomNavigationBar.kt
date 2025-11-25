import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
        modifier = Modifier.height(80.dp)
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(R.drawable.icon),
                    contentDescription = "Livres",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White  // ← Ajouter couleur
                )
            },
            label = { Text("Livres", color = Color.White) },
            selected = navController.currentDestination?.route?.startsWith("books") == true,
            onClick = { navController.navigate("books") },
            colors = NavigationBarItemDefaults.colors(  // ← Configurer les couleurs
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = Color.White.copy(alpha = 0.6f),
                unselectedTextColor = Color.White.copy(alpha = 0.6f),
                indicatorColor = Color(0xFF0277BD)
            )
        )

        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(R.drawable.recherche),
                    contentDescription = "Recherche",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            },
            label = { Text("Recherche", color = Color.White) },
            selected = navController.currentDestination?.route == "search",
            onClick = { navController.navigate("search") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = Color.White.copy(alpha = 0.6f),
                unselectedTextColor = Color.White.copy(alpha = 0.6f),
                indicatorColor = Color(0xFF0277BD)
            )
        )

        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(R.drawable.favoris),
                    contentDescription = "Favoris",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            },
            label = { Text("Favoris", color = Color.White) },
            selected = navController.currentDestination?.route == "favoris",
            onClick = { navController.navigate("favoris") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = Color.White.copy(alpha = 0.6f),
                unselectedTextColor = Color.White.copy(alpha = 0.6f),
                indicatorColor = Color(0xFF0277BD)
            )
        )
    }
}