package com.abadeer.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abadeer.todolist.ui.theme.TodoListTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoListTheme {
                ToDoApp()
            }
        }
    }
}

@Composable
fun ToDoApp() {
    var taskText by remember { mutableStateOf("") }
    val tasks = remember { mutableStateListOf<String>() }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("To-Do List") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                BasicTextField(
                    value = taskText,
                    onValueChange = { taskText = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        if (taskText.isNotBlank()) {
                            tasks.add(taskText)
                            taskText = ""
                        }
                    })
                )
                Button(onClick = {
                    if (taskText.isNotBlank()) {
                        tasks.add(taskText)
                        taskText = ""
                    }
                }) {
                    Text("Add")
                }
            }
            TaskList(tasks = tasks, onDeleteTask = { index ->
                tasks.removeAt(index)
            })
        }
    }
}

@Composable
fun TaskList(tasks: List<String>, onDeleteTask: (Int) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        tasks.forEachIndexed { index, task ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(task, style = MaterialTheme.typography.bodyLarge)
                IconButton(onClick = { onDeleteTask(index) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Task")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ToDoAppPreview() {
    TodoListTheme {
        ToDoApp()
    }
}
