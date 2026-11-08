package com.inknotes.inknotes.data

import com.inknotes.inknotes.data.model.Note
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class NoteTest {

    @Test
    fun testCreateNewNote() {
        val note = Note.createNew(folderId = 1L)
        
        assertEquals("Untitled Note", note.title)
        assertEquals(1L, note.folderId)
        assertEquals(false, note.isPinned)
        assertEquals(1, note.pageCount)
    }

    @Test
    fun testCreateNewNoteWithoutFolder() {
        val note = Note.createNew()
        
        assertEquals("Untitled Note", note.title)
        assertEquals(null, note.folderId)
    }

    @Test
    fun testNoteTimestamp() {
        val before = Date()
        val note = Note.createNew()
        val after = Date()
        
        assertEquals(true, note.createdAt.time >= before.time)
        assertEquals(true, note.createdAt.time <= after.time)
        assertEquals(true, note.updatedAt.time >= before.time)
        assertEquals(true, note.updatedAt.time <= after.time)
    }
}