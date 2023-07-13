package com.example.mynotess15.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NotesDao

    companion object {

        @Volatile
        var INSTANCE: NoteDatabase? = null;

        fun getDatabase(context: Context): NoteDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(context, NoteDatabase::class.java, "notes_database")
                            .build()
                }
            }
            return INSTANCE!!;
        }

    }
}