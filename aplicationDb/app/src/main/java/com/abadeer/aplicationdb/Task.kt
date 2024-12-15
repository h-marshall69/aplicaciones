package com.abadeer.aplicationdb

data class Task(
    val id: Long = 0,
    val title: String,
    val description: String,
    val date: String // Fecha de la tarea
)