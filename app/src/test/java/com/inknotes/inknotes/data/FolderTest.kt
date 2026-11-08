package com.inknotes.inknotes.data

import com.inknotes.inknotes.data.model.Folder
import org.junit.Assert.assertEquals
import org.junit.Test

class FolderTest {

    @Test
    fun testCreateDefaultFolders() {
        val folders = Folder.createDefaultFolders()
        
        assertEquals(8, folders.size)
        assertEquals("Math", folders[0].name)
        assertEquals("Physics", folders[1].name)
        assertEquals("Chemistry", folders[2].name)
        assertEquals("Biology", folders[3].name)
    }

    @Test
    fun testCreateUnfiledFolder() {
        val folder = Folder.createUnfiledFolder()
        
        assertEquals(Folder.UNFILED_FOLDER_ID, folder.id)
        assertEquals(Folder.UNFILED_FOLDER_NAME, folder.name)
        assertEquals(0xFF808080.toInt(), folder.color)
    }

    @Test
    fun testDefaultColors() {
        val colors = Folder.DEFAULT_COLORS
        
        assertEquals(8, colors.size)
        assertEquals(0xFFB388FF.toInt(), colors[0])
        assertEquals(0xFF8AB4F8.toInt(), colors[1])
    }
}