package com.deepakjetpackcompose.ainotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepakjetpackcompose.ainotes.model.database.Notes
import com.deepakjetpackcompose.ainotes.model.repository.NotesRepository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NotesViewmodel(application: Application): AndroidViewModel(application) {
    private val notesRepository= NotesRepository(application)

    val allTask: Flow<List<Notes>> =notesRepository.allTask

    fun addNotes(notes: Notes){
        viewModelScope.launch {
            notesRepository.insert(notes)
        }
    }

    fun deleteNotes(notes: Notes){
        viewModelScope.launch {
            notesRepository.delete(notes)
        }
    }

    fun updateNotes(notes: Notes,title:String,content:String,timeStamp: Long){
        viewModelScope.launch{
            val updatedNotes=notes.copy(title = title, content = content, timeStamp = timeStamp)
            notesRepository.update(updatedNotes)
        }
    }
}