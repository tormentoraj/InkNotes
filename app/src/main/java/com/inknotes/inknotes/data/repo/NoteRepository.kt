package com.inknotes.inknotes.data.repo

import com.inknotes.inknotes.data.dao.NoteDao
import com.inknotes.inknotes.data.dao.PageDao
import com.inknotes.inknotes.data.dao.StrokeDao
import com.inknotes.inknotes.data.model.Note
import com.inknotes.inknotes.data.model.Page
import com.inknotes.inknotes.data.model.Stroke
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.Date
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao,
    private val pageDao: PageDao,
    private val strokeDao: StrokeDao
) {
    fun getAllNotes(): Flow<List<Note>> = noteDao.getAll()

    fun getNotesByFolder(folderId: Long): Flow<List<Note>> = noteDao.getByFolder(folderId)

    fun getUnfiledNotes(): Flow<List<Note>> = noteDao.getUnfiled()

    fun getNoteById(id: Long): Flow<Note?> = noteDao.getById(id)

    suspend fun createNote(note: Note): Long {
        val noteId = noteDao.insert(note)
        val page = Page.createNew(noteId)
        pageDao.insert(page)
        return noteId
    }

    suspend fun updateNote(note: Note) = noteDao.update(note)

    suspend fun deleteNote(id: Long) {
        noteDao.delete(id)
    }

    suspend fun moveNoteToFolder(noteId: Long, folderId: Long?) {
        noteDao.moveToFolder(noteId, folderId)
        noteDao.updateTimestamp(noteId, Date())
    }

    suspend fun setNotePinned(id: Long, pinned: Boolean) {
        noteDao.setPinned(id, pinned)
    }

    fun searchNotes(query: String): Flow<List<Note>> {
        val searchPattern = "%$query%"
        return noteDao.search(searchPattern)
    }

    suspend fun getNoteWithPages(noteId: Long): Pair<Note?, List<Page>> {
        val note = noteDao.getByIdOnce(noteId)
        val pages = pageDao.getByNoteOnce(noteId)
        return note to pages
    }

    suspend fun getPageWithStrokes(pageId: Long): Pair<Page?, List<Stroke>> {
        val page = pageDao.getByIdOnce(pageId)
        val strokes = strokeDao.getByPageOnce(pageId)
        return page to strokes
    }

    suspend fun addPage(noteId: Long): Page {
        val pages = pageDao.getByNoteOnce(noteId)
        val newPage = Page.createNew(noteId).copy(
            sortOrder = pages.size
        )
        val pageId = pageDao.insert(newPage)
        return newPage.copy(id = pageId)
    }

    suspend fun deletePage(pageId: Long) {
        strokeDao.deleteByPage(pageId)
        pageDao.delete(pageId)
    }

    suspend fun reorderPages(noteId: Long, newOrder: List<Long>) {
        newOrder.forEachIndexed { index, pageId ->
            pageDao.updateSortOrder(pageId, index)
        }
    }

    suspend fun updateNoteTimestamp(noteId: Long) {
        noteDao.updateTimestamp(noteId, Date())
    }
}