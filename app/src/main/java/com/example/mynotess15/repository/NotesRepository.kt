package com.example.mynotess15.repository

import androidx.lifecycle.LiveData
import com.example.mynotess15.database.Note
import com.example.mynotess15.database.NotesDao

class NotesRepository(val dao : NotesDao) {

    fun getAllNotes(): LiveData<List<Note>>{
        return dao.getAllNotes()
    }

    suspend fun insert(note: Note){
        return dao.insert(note)
    }

    suspend fun update(note: Note){
       return dao.update(note)
    }

    suspend fun delete(note: Note){
        return dao.delete(note)
    }
}