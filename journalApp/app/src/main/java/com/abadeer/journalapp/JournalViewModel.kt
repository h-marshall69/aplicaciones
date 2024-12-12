package com.abadeer.journalapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class JournalViewModel(private val dao: JournalDao) : ViewModel() {
    val entries: Flow<List<JournalEntry>> = dao.getAllEntries()

    fun addEntry(entry: JournalEntry) {
        viewModelScope.launch {
            dao.insert(entry)
        }
    }
}

class JournalViewModelFactory(private val dao: JournalDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JournalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JournalViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}