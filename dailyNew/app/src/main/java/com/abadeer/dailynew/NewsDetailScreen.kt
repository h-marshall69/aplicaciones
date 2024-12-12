package com.abadeer.dailynew

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NewsDetailScreen(newsId: String) {
    // Example of getting article details
    val article = NewsArticle(newsId, "Title $newsId", "Detailed description of the article", "https://example.com")

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = article.title)
        Text(text = article.description)
    }
}