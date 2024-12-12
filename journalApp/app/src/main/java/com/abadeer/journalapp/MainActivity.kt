package com.abadeer.journalapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.abadeer.journalapp.ui.theme.JournalAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = JournalDatabase.getDatabase(this)
        val dao = database.journalDao()
        //enableEdgeToEdge()
        setContent {
            JournalAppTheme {
                JournalScreen(
                    viewModel = JournalViewModel(dao)
                )
            }
        }
    }
}

@Composable
fun JournalApp() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { padding ->
            JournalScreen(Modifier.padding(padding))
        }
    )
}