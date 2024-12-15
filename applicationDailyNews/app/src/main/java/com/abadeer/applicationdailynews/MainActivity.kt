package com.abadeer.applicationdailynews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abadeer.applicationdailynews.ui.theme.ApplicationDailyNewsTheme
import java.text.SimpleDateFormat
import java.util.*

data class NewsArticle(
    val title: String,
    val description: String,
    val date: String
)

// Generador de noticias falsas
fun fakeNews(): List<NewsArticle> {
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val currentDate = formatter.format(Date())
    return listOf(
        NewsArticle("Robot discovers new planet!", "A new planet was discovered by AI robots. Experts are calling it 'RoboEarth'.", currentDate),
        NewsArticle("Cats take over the internet again", "A viral video of dancing cats has taken social media by storm.", currentDate),
        NewsArticle("Self-driving bikes launched", "Tech company unveils first self-driving bicycles for smart cities.", currentDate),
        NewsArticle("Space tourism booming in 2025", "Private companies report a surge in space tourism bookings.", currentDate),
        NewsArticle("Virtual reality concerts gain popularity", "Fans now enjoy live concerts in fully immersive VR worlds.", currentDate)
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApplicationDailyNewsTheme {
                NewsScreen(fakeNews())
            }
        }
    }
}

// Definición del tema personalizado
private val CustomColorScheme = darkColorScheme(
    primary = Color(0xFF80CBC4),       // Verde agua para el texto primario
    onPrimary = Color(0xFF004D40),    // Verde oscuro para fondos claros
    background = Color(0xFF121212),   // Negro para el fondo principal
    surface = Color(0xFF1E1E1E),      // Gris oscuro para tarjetas
    onSurface = Color(0xFFE0E0E0),    // Blanco para texto en tarjetas
)

// Pantalla principal
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(newsArticles: List<NewsArticle>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daily News", color = CustomColorScheme.primary) },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = CustomColorScheme.surface
                )
            )
        },
        containerColor = CustomColorScheme.background // Fondo del Scaffold
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(newsArticles) { article ->
                NewsItem(article)
            }
        }
    }
}

// Item individual de una noticia
@Composable
fun NewsItem(newsArticle: NewsArticle) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { /* Acción al hacer clic, como abrir un detalle de noticia */ },
        colors = CardDefaults.cardColors(
            containerColor = CustomColorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = newsArticle.title,
                style = MaterialTheme.typography.titleMedium,
                color = CustomColorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = newsArticle.description,
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Published: ${newsArticle.date}",
                style = MaterialTheme.typography.bodySmall,
                color = CustomColorScheme.onPrimary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsScreenPreview() {
    ApplicationDailyNewsTheme {
        NewsScreen(fakeNews())
    }
}
