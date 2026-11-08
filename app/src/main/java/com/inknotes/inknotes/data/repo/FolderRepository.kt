package com.inknotes.inknotes.data.repo

import com.inknotes.inknotes.data.dao.FolderDao
import com.inknotes.inknotes.data.dao.NoteDao
import com.inknotes.inknotes.data.model.Folder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class FolderRepository @Inject constructor(
    private val folderDao: FolderDao,
    private val noteDao: NoteDao
) {
    fun getAllFolders(): Flow<List<Folder>> = folderDao.getAll()

    fun getFolderById(id: Long): Flow<Folder?> = folderDao.getById(id)

    suspend fun createFolder(folder: Folder): Long = folderDao.insert(folder)

    suspend fun updateFolder(folder: Folder) = folderDao.update(folder)

    suspend fun deleteFolder(id: Long) {
        folderDao.delete(id)
    }

    fun getFolderWithNoteCount(folderId: Long): Flow<Pair<Folder?, Int>> {
        return combine(
            folderDao.getById(folderId),
            noteDao.getCountByFolder(folderId)
        ) { folder, count ->
            folder to count
        }
    }

    fun getAllFoldersWithNoteCounts(): Flow<List<Pair<Folder, Int>>> {
        return combine(
            folderDao.getAll(),
            noteDao.getTotalCount()
        ) { folders, _ ->
            folders.map { folder ->
                folder to 0
            }
        }
    }

    suspend fun getNextSortOrder(): Int = folderDao.getNextSortOrder() ?: 0

    suspend fun reorderFolders(folders: List<Folder>) {
        folders.forEachIndexed { index, folder ->
            folderDao.updateSortOrder(folder.id, index)
        }
    }

    suspend fun createDefaultFolders() {
        val existingCount = folderDao.getCount()
        if (existingCount == 0) {
            val defaultFolders = Folder.createDefaultFolders()
            folderDao.insertAll(defaultFolders)
        }
    }
}