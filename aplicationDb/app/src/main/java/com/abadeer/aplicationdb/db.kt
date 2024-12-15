package com.abadeer.aplicationdb

import android.content.Context
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "bullet_journal.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "tasks"
        private const val COL_ID = "id"
        private const val COL_TITLE = "title"
        private const val COL_DESCRIPTION = "description"
        private const val COL_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_TITLE TEXT,
                $COL_DESCRIPTION TEXT,
                $COL_DATE TEXT
            )
        """
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    // Insertar una nueva tarea
    fun insertTask(task: Task) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_TITLE, task.title)
            put(COL_DESCRIPTION, task.description)
            put(COL_DATE, task.date)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    // Obtener todas las tareas
    fun getAllTasks(): List<Task> {
        val tasks = mutableListOf<Task>()
        val db = readableDatabase
        val cursor: Cursor = db.query(TABLE_NAME, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndex(COL_ID))
                val title = cursor.getString(cursor.getColumnIndex(COL_TITLE))
                val description = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION))
                val date = cursor.getString(cursor.getColumnIndex(COL_DATE))
                tasks.add(Task(id, title, description, date))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return tasks
    }

    // Eliminar una tarea
    fun deleteTask(taskId: Long) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COL_ID = ?", arrayOf(taskId.toString()))
        db.close()
    }

    // Actualizar una tarea
    fun updateTask(task: Task) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_TITLE, task.title)
            put(COL_DESCRIPTION, task.description)
            put(COL_DATE, task.date)
        }
        db.update(TABLE_NAME, values, "$COL_ID = ?", arrayOf(task.id.toString()))
        db.close()
    }
}