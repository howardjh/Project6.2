package com.example.project6

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NotesViewModel(private val dao: NoteDao) : ViewModel() {
    private var newNoteName = ""
    private var newNoteDescription = ""
    val notes = dao.getAll()
    private val _navigateToNote = MutableLiveData<Long?>()
    val navigateToNote: LiveData<Long?>
        get() = _navigateToNote
    fun addNote(): Note{
        val note = Note()
        viewModelScope.launch {
            note.noteName = newNoteName
            note.noteDescription = newNoteDescription
            dao.insert(note)
        }
        return note
    }

    fun deleteNote(noteId: Long){
        viewModelScope.launch{
            val note = Note()
            note.noteId = noteId
            dao.delete(note)
        }
    }
    fun onNoteClicked(noteId: Long) {
        _navigateToNote.value = noteId
    }
    fun onNoteNavigated() {
        _navigateToNote.value = null
    }
}
