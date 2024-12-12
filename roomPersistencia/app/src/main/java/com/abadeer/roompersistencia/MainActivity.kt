package com.abadeer.roompersistencia

import android.app.Application
import android.os.Bundle
import android.provider.ContactsContract
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
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abadeer.roompersistencia.ui.theme.RoomPersistenciaTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoomPersistenciaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// Repository
class NoteRepository(private val noteDao: NoteDao) {
    suspend fun getAllNotes(): List<ContactsContract.CommonDataKinds.Note> = noteDao.getAllNotes()
    suspend fun insert(note: ContactsContract.CommonDataKinds.Note) = noteDao.insert(note)
}

// ViewModel
class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val noteDao = AppDatabase.getDatabase(application).noteDao()
    private val repository = NoteRepository(noteDao)

    private val _notes = MutableLiveData<List<ContactsContract.CommonDataKinds.Note>>()
    val notes: LiveData<List<ContactsContract.CommonDataKinds.Note>> get() = _notes

    fun getAllNotes() {
        viewModelScope.launch {
            _notes.value = repository.getAllNotes()
        }
    }

    fun addNote(note: ContactsContract.CommonDataKinds.Note) {
        viewModelScope.launch {
            repository.insert(note)
        }
    }
}

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String
)

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String
)

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: Note)

    @Query("SELECT * FROM notes")
    suspend fun getAllNotes(): List<Note>
}

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "notes_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}