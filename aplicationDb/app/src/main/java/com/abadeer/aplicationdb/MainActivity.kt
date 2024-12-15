package com.abadeer.aplicationdb

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abadeer.aplicationdb.ui.theme.AplicationDbTheme
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.abadeer.aplicationdb.ui.theme.AplicationDbTheme
import java.util.Date


class MainActivity : ComponentActivity() {
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = DBHelper(this)
        //enableEdgeToEdge()
        setContent {
            AplicationDbTheme  {
                BulletJournalScreen(dbHelper)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BulletJournalScreen(dbHelper: DBHelper) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var tasks by remember { mutableStateOf(dbHelper.getAllTasks()) }

    var isEditing by remember { mutableStateOf(false) }
    var taskBeingEdited by remember { mutableStateOf<Task?>(null) }

    val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Bullet Journal", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para título
        androidx.compose.material3.OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo para descripción
        androidx.compose.material3.OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de guardar
        Button(
            onClick = {
                if (title.isNotEmpty() && description.isNotEmpty()) {
                    if (isEditing && taskBeingEdited != null) {
                        // Actualizar tarea existente
                        val updatedTask = taskBeingEdited!!.copy(title = title, description = description)
                        dbHelper.updateTask(updatedTask)
                        tasks = dbHelper.getAllTasks() // Refrescar lista
                        isEditing = false
                        taskBeingEdited = null
                    } else {
                        // Agregar nueva tarea
                        val newTask = Task(title = title, description = description, date = currentDate)
                        dbHelper.insertTask(newTask)
                        tasks = dbHelper.getAllTasks() // Refrescar lista
                    }
                    title = ""
                    description = ""
                } else {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isEditing) "Update Task" else "Add Task")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de tareas
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(tasks) { task ->
                TaskItem(task, dbHelper, onDelete = {
                    tasks = dbHelper.getAllTasks() // Refrescar la lista después de eliminar
                }, onEdit = {
                    // Preparar para editar tarea
                    isEditing = true
                    taskBeingEdited = task
                    title = task.title
                    description = task.description
                })
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, dbHelper: DBHelper, onDelete: () -> Unit, onEdit: () -> Unit) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Title: ${task.title}", style = MaterialTheme.typography.titleMedium)
            Text("Description: ${task.description}", style = MaterialTheme.typography.bodyMedium)
            Text("Date: ${task.date}", style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    dbHelper.deleteTask(task.id)
                    onDelete()
                }) {
                    Text("Delete")
                }

                Button(onClick = {
                    onEdit() // Llamar a la función de editar
                }) {
                    Text("Edit")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AplicationDbTheme {
        BulletJournalScreen(DBHelper(LocalContext.current))
    }
}