package view

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.monapp.R

@Composable
fun HomeScreen(navController: NavHostController, modifier: Modifier = Modifier) {

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFB3E5FC), Color(0xFFE1F5FE))
    )

    val buttonModifier = Modifier
        .width(200.dp)
        .height(50.dp)

    if (isPortrait) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(backgroundGradient)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.book),
                contentDescription = "Livres",
                modifier = Modifier
                    .size(180.dp)
                    .padding(bottom = 24.dp)
            )

            Text(
                text = "Bienvenue dans Ma Bibliothèque",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { navController.navigate("books") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0288D1)),
                shape = RoundedCornerShape(20.dp),
                modifier = buttonModifier
            ) {
                Text(
                    text = "La liste des livres",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }
        }
    } else {
        Row(
            modifier = modifier
                .fillMaxSize()
                .background(backgroundGradient)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.book),
                contentDescription = "Livres",
                modifier = Modifier
                    .size(150.dp)
                    .padding(end = 24.dp)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Bienvenue dans Ma Bibliothèque",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { navController.navigate("books") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0288D1)),
                    shape = RoundedCornerShape(20.dp),
                    modifier = buttonModifier
                ) {
                    Text(
                        text = "La liste des livres",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
            }
        }
    }
}

