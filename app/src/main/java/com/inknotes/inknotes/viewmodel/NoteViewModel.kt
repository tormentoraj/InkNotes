package com.inknotes.inknotes.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inknotes.inknotes.data.model.Note
import com.inknotes.inknotes.data.repo.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val currentFolderId: StateFlow<Long?> = savedStateHandle.getStateFlow("folderId", null)

    val notes: StateFlow<List<Note>> = currentFolderId
        .combine(noteRepository.getAllNotes()) { folderId, allNotes ->
            if (folderId == null) {
                allNotes
            } else {
                allNotes.filter { it.folderId == folderId }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val searchQuery: StateFlow<String> = savedStateHandle.getStateFlow("searchQuery", "")

    val searchResults: StateFlow<List<Note>> = searchQuery
        .combine(noteRepository.getAllNotes()) { query, allNotes ->
            if (query.isBlank()) {
                allNotes
            } else {
                allNotes.filter {
                    it.title.contains(query, ignoreCase = true) ||
                    (it.searchText?.contains(query, ignoreCase = true) == true)
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setCurrentFolder(folderId: Long?) {
        savedStateHandle["folderId"] = folderId
    }

    fun setSearchQuery(query: String) {
        savedStateHandle["searchQuery"] = query
    }

    fun createNote(folderId: Long? = null): Long? {
        var noteId: Long? = null
        viewModelScope.launch {
            val note = Note.createNew(folderId)
            noteId = noteRepository.createNote(note)
        }
        return noteId
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            noteRepository.deleteNote(noteId)
        }
    }

    fun moveNoteToFolder(noteId: Long, folderId: Long?) {
        viewModelScope.launch {
            noteRepository.moveNoteToFolder(noteId, folderId)
        }
    }

    fun setNotePinned(noteId: Long, pinned: Boolean) {
        viewModelScope.launch {
            noteRepository.setNotePinned(noteId, pinned)
        }
    }

    fun refreshNotes() {
        viewModelScope.launch {
        }
    }
}