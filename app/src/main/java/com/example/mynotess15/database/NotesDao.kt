package com.example.mynotess15.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface NotesDao {

    @Query("Select * From notes_table Order By id Desc")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Update
    suspend fun update(note: Note)

}