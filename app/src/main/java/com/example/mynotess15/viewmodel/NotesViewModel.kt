package com.example.mynotess15.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mynotess15.database.Note
import com.example.mynotess15.database.NoteDatabase
import com.example.mynotess15.database.NotesDao
import com.example.mynotess15.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList
import java.util.Locale

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: NotesRepository
    private var dao: NotesDao
    private lateinit var tempNote: Note
    private var isUpdate = false
    private var noteList = ArrayList<Note>()
    private lateinit var searchQuery: String
    var isSearching = false
    var isLoading = false
    private var filteredList : List<Note>

    init {
        Log.d("TAG", "NotesViewModel : init ")
        dao = NoteDatabase.getDatabase(application).noteDao()
        repository = NotesRepository(dao)
        tempNote = Note(0, "", "", "", "", "")
        filteredList = ArrayList()
        searchQuery = ""
    }

    fun setSearchQuery(query: String) {
        Log.d("TAG", "setSearchQuery: ")
        searchQuery = query
    }

    fun getSearchQuery(): String {
        Log.d("TAG", "getSearchQuery: ")
        return searchQuery
    }

    fun setFilteredList(list: List<Note>?) {
        Log.d("TAG", "setFilteredList: ")
        if (list != null) {
            filteredList = list
        }
    }

    fun getFilteredList(): List<Note> {
        Log.d("TAG", "getFilteredList: ")
        return filteredList
    }

    fun getTempNote(): Note {
        Log.d("TAG", "getTempNote: ")
        return tempNote
    }

    fun setTempNote(note: Note) {
        Log.d("TAG", "setTempNote: ")
        tempNote = note
    }

    fun setIsUpdate(input :Boolean) {
        Log.d("TAG", "setIsUpdate: ")
        isUpdate = input
        Log.d("TAG", "isUpdate : $isUpdate ")
    }

    fun getIsUpdate(): Boolean {
        Log.d("TAG", "getIsUpdate: ")
        return isUpdate
    }

    fun searchTask(searchText: String): List<Note>? {
        Log.d("TAG", "searchTask: ")

        val filteredList = noteList.filter { note ->

            note.title.lowercase(Locale.ROOT).contains(searchText.lowercase(Locale.ROOT)) ||
                    note.description.lowercase(Locale.ROOT)
                        .contains(searchText.lowercase(Locale.ROOT))
        }
        return filteredList
    }

    fun saveAllNotes(list : ArrayList<Note>) {
        noteList.clear()
        noteList = list
    }
    fun getAllNotes(): LiveData<List<Note>>{
        Log.d("TAG", "getAllNotes: ")
        return repository.getAllNotes()
    }


    fun insert(note: Note) {
        Log.d("TAG", "insert: ")
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(note)
        }

    }

    fun update(note: Note) {
        Log.d("TAG", "update: ")
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(note)
        }
    }

    fun delete(note: Note) {
        Log.d("TAG", "delete: ")
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(note)
        }
    }
}