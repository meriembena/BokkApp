package ecrans

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.monapp.MainViewModel

@Composable
fun Screen() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel()

    Scaffold(
        topBar = { AppBar(viewModel) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->
        AppNavHost(navController, modifier = Modifier.padding(padding))
    }
}