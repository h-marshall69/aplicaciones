package com.abadeer.dailynew

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.navigation.NavController

@Composable
fun NewsListScreen(navController: NavController) {
    // Example list of articles
    val newsList = listOf(
        NewsArticle("1", "Title 1", "Description 1", "https://example.com"),
        NewsArticle("2", "Title 2", "Description 2", "https://example.com")
    )

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(newsList) { article ->
            Card(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .clickable { navController.navigate("news_detail/${article.id}") }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = article.title)
                    Text(text = article.description)
                }
            }
        }
    }
}