import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
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
        modifier = Modifier.height(50.dp)
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    painterResource(R.drawable.icon),
                    contentDescription = "Livres",
                    modifier = Modifier.size(30.dp)
                )
            },
            label = {
                Text(
                    "Livres",
                    style = MaterialTheme.typography.labelSmall
                )
            },
            selected = navController.currentDestination?.route == "books",
            onClick = { navController.navigate("books") }
        )

        NavigationBarItem(
            icon = { Icon(painterResource(R.drawable.favoris), contentDescription = "Favoris", modifier = Modifier.size(30.dp)) },
            label = { Text("Favoris", style = MaterialTheme.typography.labelSmall) },
            selected = navController.currentDestination?.route == "favoris",
            onClick = { navController.navigate("favoris") }
        )

    }
}
