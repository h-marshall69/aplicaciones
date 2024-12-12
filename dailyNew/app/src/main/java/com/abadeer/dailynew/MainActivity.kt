package com.abadeer.dailynew

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abadeer.dailynew.ui.theme.DailyNewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DailyNewTheme {
                // Setup the navigation
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { TopAppBar(title = { Text("Daily News") }) }
                ) { innerPadding ->
                    // NavHost to switch between screens
                    NavHost(
                        navController = navController,
                        startDestination = "news_list",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("news_list") {
                            NewsListScreen(navController)
                        }
                        composable("news_detail/{newsId}") { backStackEntry ->
                            val newsId = backStackEntry.arguments?.getString("newsId")
                            NewsDetailScreen(newsId = newsId ?: "")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DailyNewTheme {
        NewsListScreen(navController = rememberNavController())
    }
}
