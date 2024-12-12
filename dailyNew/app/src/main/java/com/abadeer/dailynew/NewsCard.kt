package com.abadeer.dailynew

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NewsCard(article: NewsArticle, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = article.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = article.description, style = MaterialTheme.typography.bodySmall)
        }
    }
}