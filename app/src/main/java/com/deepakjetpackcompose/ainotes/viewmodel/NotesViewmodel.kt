package com.deepakjetpackcompose.ainotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.deepakjetpackcompose.ainotes.model.database.Notes
import com.deepakjetpackcompose.ainotes.model.repository.ApiRepository
import com.deepakjetpackcompose.ainotes.model.repository.NotesRepository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotesViewmodel(application: Application): AndroidViewModel(application) {
    private val notesRepository= NotesRepository(application)
    private val apiRepository= ApiRepository()
    private val _isDark= MutableStateFlow<Boolean>(false)
    val isDark: StateFlow<Boolean> = _isDark.asStateFlow()

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

    fun getSummaries(content: String): String{
        var ans=""
        viewModelScope.launch {
            ans= apiRepository.getSummary(content).candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: "No summary found"
        }
        return ans
    }

    fun getTranslation(content: String,lang: String): String{
        var ans=""
        viewModelScope.launch {
            ans= apiRepository.getTranslation(content=content,lang=lang).candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: "No Translation found for $lang"
        }
        return ans
    }

    fun toggleDarkMode(){
        _isDark.value = ! _isDark.value
    }
}