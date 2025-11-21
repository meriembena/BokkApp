package ecrans

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.monapp.R

@Composable
fun HomeScreen(navController: androidx.navigation.NavHostController) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    if (isPortrait) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileSection()
            Spacer(modifier = Modifier.height(20.dp))
            ContactSection(context)
            Spacer(modifier = Modifier.height(30.dp))
            Button(onClick = { navController.navigate("films") }) {
                Text(text = "Démarrer")
            }
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileSection()
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ContactSection(context)
                Spacer(modifier = Modifier.height(30.dp))
                Button(onClick = { navController.navigate("films") }) {
                    Text(text = "Démarrer")
                }
            }
        }
    }
}

@Composable
fun ProfileSection() {
    Image(
        painter = painterResource(id = R.drawable.maphoto),
        contentDescription = "Ma photo",
        modifier = Modifier
            .size(150.dp)
            .clip(CircleShape)
    )
    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = "Yosr BEN JABEUR",
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(8.dp)
    )
    Spacer(modifier = Modifier.height(5.dp))
    Text(
        text = "Étudiante développeuse passionnée par la création d'apps innovantes.",
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    )
}

@Composable
fun ContactSection(context: android.content.Context) {
    Text(
        text = "Contact : yosr.benjabeur@gmail.com",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(8.dp)
    )
    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = "LinkedIn",
        style = MaterialTheme.typography.bodyLarge.copy(
            color = Color(0xFF0A66C2),
            textDecoration = TextDecoration.Underline
        ),
        modifier = Modifier
            .clickable {
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/yosr-jabeur"))
                context.startActivity(intent)
            }
            .padding(8.dp)
    )
}