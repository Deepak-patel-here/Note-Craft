package com.deepakjetpackcompose.ainotes.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.deepakjetpackcompose.ainotes.model.client.ApiClient
import com.deepakjetpackcompose.ainotes.model.database.Notes
import com.deepakjetpackcompose.ainotes.model.repository.ApiRepository
import com.deepakjetpackcompose.ainotes.model.repository.NotesRepository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NotesViewmodel(application: Application): AndroidViewModel(application) {
    private val notesRepository= NotesRepository(application)
    private val apiRepository= ApiRepository()
    private val _isDark= MutableStateFlow<Boolean>(false)
    val isDark: StateFlow<Boolean> = _isDark.asStateFlow()
    private val _fetch= MutableStateFlow<ApiState>(ApiState.UnLoading)
    val fetch: StateFlow<ApiState> = _fetch.asStateFlow()
    var translatedText = mutableStateOf("")
    val summariesText = mutableStateOf("")


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

    fun updateNotes(notes: Notes){
        viewModelScope.launch{
            val updatedNotes=notes.copy()
            notesRepository.update(updatedNotes)
        }
    }

    fun getSummaries(content: String){
        _fetch.value= ApiState.Summary
        var ans=""
        viewModelScope.launch {
            ans= apiRepository.getSummary(content).candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: "No summary found"
            summariesText.value=ans
            _fetch.value= ApiState.UnLoading

        }

    }

    fun getTranslation(content: String,lang: String){
        _fetch.value= ApiState.Translate
        var ans=""
        viewModelScope.launch {

            ans= apiRepository.getTranslation(content=content,lang=lang).candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: "No Translation found for $lang"
            Log.d("hindi",ans)
            translatedText.value=ans
            _fetch.value= ApiState.UnLoading

        }
    }

    fun toggleDarkMode(){
        _isDark.value = ! _isDark.value
    }
}
sealed class ApiState(){
    object Summary: ApiState()
    object Translate: ApiState()
    object UnLoading: ApiState()

}

