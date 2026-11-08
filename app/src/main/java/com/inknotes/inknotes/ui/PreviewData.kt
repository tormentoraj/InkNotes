package com.inknotes.inknotes.ui

import com.inknotes.inknotes.data.model.Folder
import com.inknotes.inknotes.data.model.Note
import com.inknotes.inknotes.data.model.Page
import com.inknotes.inknotes.data.model.Stroke
import java.util.Date

object PreviewData {
    
    fun createSampleFolders(): List<Folder> {
        return listOf(
            Folder(
                id = 1,
                name = "Math",
                color = 0xFFB388FF.toInt(),
                icon = "math",
                createdAt = Date(),
                updatedAt = Date(),
                sortOrder = 0
            ),
            Folder(
                id = 2,
                name = "Physics",
                color = 0xFF8AB4F8.toInt(),
                icon = "physics",
                createdAt = Date(),
                updatedAt = Date(),
                sortOrder = 1
            ),
            Folder(
                id = 3,
                name = "Chemistry",
                color = 0xFF81C9FA.toInt(),
                icon = "chemistry",
                createdAt = Date(),
                updatedAt = Date(),
                sortOrder = 2
            ),
            Folder(
                id = 4,
                name = "Biology",
                color = 0xFF8CD583.toInt(),
                icon = "biology",
                createdAt = Date(),
                updatedAt = Date(),
                sortOrder = 3
            ),
            Folder(
                id = 5,
                name = "History",
                color = 0xFFFFB470.toInt(),
                icon = "history",
                createdAt = Date(),
                updatedAt = Date(),
                sortOrder = 4
            ),
            Folder(
                id = 6,
                name = "Notes",
                color = 0xFFFF8A85.toInt(),
                icon = null,
                createdAt = Date(),
                updatedAt = Date(),
                sortOrder = 5
            )
        )
    }
    
    fun createSampleNotes(folderId: Long? = null): List<Note> {
        val now = Date()
        val yesterday = Date(now.time - 86400000)
        val lastWeek = Date(now.time - 604800000)
        
        return listOf(
            Note(
                id = 1,
                title = "Calculus Homework",
                folderId = folderId ?: 1,
                createdAt = lastWeek,
                updatedAt = yesterday,
                isPinned = true,
                thumbnailPath = null,
                pageCount = 2,
                searchText = "integral derivative limit"
            ),
            Note(
                id = 2,
                title = "Physics Lab Report",
                folderId = folderId ?: 2,
                createdAt = lastWeek,
                updatedAt = now,
                isPinned = false,
                thumbnailPath = null,
                pageCount = 3,
                searchText = "experiment results data"
            ),
            Note(
                id = 3,
                title = "Chemistry Notes",
                folderId = folderId ?: 3,
                createdAt = yesterday,
                updatedAt = now,
                isPinned = true,
                thumbnailPath = null,
                pageCount = 1,
                searchText = "reactions elements"
            ),
            Note(
                id = 4,
                title = "Biology Essay",
                folderId = folderId ?: 4,
                createdAt = lastWeek,
                updatedAt = yesterday,
                isPinned = false,
                thumbnailPath = null,
                pageCount = 4,
                searchText = "cells DNA genetics"
            ),
            Note(
                id = 5,
                title = "History Timeline",
                folderId = folderId ?: 5,
                createdAt = lastWeek,
                updatedAt = now,
                isPinned = false,
                thumbnailPath = null,
                pageCount = 2,
                searchText = "events timeline dates"
            ),
            Note(
                id = 6,
                title = "Untitled Note",
                folderId = folderId,
                createdAt = now,
                updatedAt = now,
                isPinned = false,
                thumbnailPath = null,
                pageCount = 1,
                searchText = null
            )
        )
    }
    
    fun createSamplePages(noteId: Long): List<Page> {
        return listOf(
            Page(
                id = 1,
                noteId = noteId,
                sortOrder = 0,
                backgroundColor = 0xFFFFFFFF.toInt(),
                createdAt = Date(),
                updatedAt = Date(),
                thumbnailPath = null,
                width = 1080,
                height = 1920
            ),
            Page(
                id = 2,
                noteId = noteId,
                sortOrder = 1,
                backgroundColor = 0xFFFFFFFF.toInt(),
                createdAt = Date(),
                updatedAt = Date(),
                thumbnailPath = null,
                width = 1080,
                height = 1920
            )
        )
    }
    
    fun createSampleStrokes(pageId: Long): List<Stroke> {
        return listOf(
            Stroke(
                id = 1,
                pageId = pageId,
                points = listOf(100f, 100f, 200f, 200f, 300f, 300f),
                pressures = listOf(0.5f, 0.7f, 0.9f),
                timestamps = listOf(System.currentTimeMillis() - 2000, 
                                  System.currentTimeMillis() - 1000, 
                                  System.currentTimeMillis()),
                color = 0xFF000000.toInt(),
                width = 3f,
                toolType = Stroke.ToolType.PEN,
                sortOrder = 0
            ),
            Stroke(
                id = 2,
                pageId = pageId,
                points = listOf(400f, 400f, 500f, 500f, 600f, 600f),
                pressures = listOf(0.5f, 0.7f, 0.9f),
                timestamps = listOf(System.currentTimeMillis() - 1500, 
                                  System.currentTimeMillis() - 500, 
                                  System.currentTimeMillis() - 0),
                color = 0xFF0000FF.toInt(),
                width = 3f,
                toolType = Stroke.ToolType.PEN,
                sortOrder = 1
            ),
            Stroke(
                id = 3,
                pageId = pageId,
                points = listOf(100f, 400f, 200f, 500f, 300f, 600f),
                pressures = listOf(0.5f, 0.7f, 0.9f),
                timestamps = listOf(System.currentTimeMillis() - 1000, 
                                  System.currentTimeMillis() - 0),
                color = 0x80FFFF00.toInt(),
                width = 8f,
                toolType = Stroke.ToolType.MARKER,
                sortOrder = 2
            )
        )
    }
}