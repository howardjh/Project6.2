package com.example.project6

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EditNoteViewModel(noteId: Long, val dao: NoteDao) : ViewModel() {
    var note = MutableLiveData<Note>()
    val noteId : Long = noteId
    private val _navigateToList = MutableLiveData<Boolean>(false)
    val navigateToList: LiveData<Boolean>
        get() = _navigateToList

    init {
        dao.get(noteId).observeForever{ it ->
            if(it == null) {
                note.value = Note()
            } else {
                note.value = it
            }
        }

    }
    fun updateNote() {
        viewModelScope.launch {
            if(note.value?.noteId != 0L) {
                dao.update(note.value!!)
            } else {
                dao.insert(note.value!!)
            }
            _navigateToList.value = true
        }
    }
    fun deleteNote() {
        viewModelScope.launch {
            dao.delete(note.value!!)
            _navigateToList.value = true
        }
    }
    fun onNavigatedToList() {
        _navigateToList.value = false
    }
}
