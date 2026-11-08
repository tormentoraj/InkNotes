package com.inknotes.inknotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inknotes.inknotes.data.model.Folder
import com.inknotes.inknotes.data.repo.FolderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FolderViewModel @Inject constructor(
    private val folderRepository: FolderRepository
) : ViewModel() {

    val folders: StateFlow<List<Folder>> = folderRepository.getAllFolders()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            folderRepository.createDefaultFolders()
        }
    }

    fun createFolder(name: String, color: Int, icon: String? = null) {
        viewModelScope.launch {
            val sortOrder = folderRepository.getNextSortOrder()
            val folder = Folder(
                name = name,
                color = color,
                icon = icon,
                sortOrder = sortOrder
            )
            folderRepository.createFolder(folder)
        }
    }

    fun updateFolder(folder: Folder) {
        viewModelScope.launch {
            folderRepository.updateFolder(folder)
        }
    }

    fun deleteFolder(folderId: Long) {
        viewModelScope.launch {
            folderRepository.deleteFolder(folderId)
        }
    }

    fun reorderFolders(folders: List<Folder>) {
        viewModelScope.launch {
            folderRepository.reorderFolders(folders)
        }
    }
}