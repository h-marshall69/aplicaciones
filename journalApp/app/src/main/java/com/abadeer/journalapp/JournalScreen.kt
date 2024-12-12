package com.abadeer.journalapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun JournalScreen(
    val context = LocalContext.current
            viewModel: JournalViewModel = viewModel(factory = JournalViewModelFactory(JournalDatabase.getDatabase(context).journalDao()))
    modifier: Modifier = Modifier,
    viewModel: JournalViewModel = viewModel(factory = JournalViewModelFactory(JournalDatabase.getDatabase(context).journalDao()))
) {
    val entries by viewModel.entries.collectAsState(initial = emptyList())
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Input Fields
        Text(
            text = "Add a New Journal Entry",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        BasicTextField(
            value = title,
            onValueChange = { title = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            decorationBox = { innerTextField ->
                Column(
                    modifier = Modifier
                        .background(Color(0xFFF5F5F5), MaterialTheme.shapes.medium)
                        .padding(12.dp)
                ) {
                    if (title.isEmpty()) Text("Title", color = Color.Gray)
                    innerTextField()
                }
            }
        )
        BasicTextField(
            value = content,
            onValueChange = { content = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            decorationBox = { innerTextField ->
                Column(
                    modifier = Modifier
                        .background(Color(0xFFF5F5F5), MaterialTheme.shapes.medium)
                        .padding(12.dp)
                ) {
                    if (content.isEmpty()) Text("Write your thoughts here...", color = Color.Gray)
                    innerTextField()
                }
            }
        )

        // Add Entry Button
        Button(
            onClick = {
                if (title.isNotBlank() && content.isNotBlank()) {
                    viewModel.addEntry(
                        JournalEntry(
                            title = title,
                            content = content,
                            timestamp = System.currentTimeMillis()
                        )
                    )
                    title = ""
                    content = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add Entry")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Display Entries
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(entries) { entry ->
                JournalEntryCard(entry)
            }
        }
    }
}