package com.deepakjetpackcompose.ainotes.model.repository

import android.app.Application
import com.deepakjetpackcompose.ainotes.model.database.Notes
import com.deepakjetpackcompose.ainotes.model.database.NotesDao
import com.deepakjetpackcompose.ainotes.model.database.NotesDb
import kotlinx.coroutines.flow.Flow

class NotesRepository(application: Application) {
    private val notesDao: NotesDao= NotesDb.getDb(application).getDao()
    val allTask: Flow<List<Notes>> = notesDao.getAllNotes()

    suspend fun insert(notes: Notes){
        notesDao.insertNotes(notes)
    }
    suspend fun delete(notes: Notes){
        notesDao.deleteNotes(notes)
    }
    suspend fun update(notes: Notes){
        notesDao.updateNotes(notes)
    }

}