package com.inknotes.inknotes.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.inknotes.inknotes.data.model.Page
import kotlinx.coroutines.flow.Flow

@Dao
interface PageDao {
    @Insert
    suspend fun insert(page: Page): Long

    @Update
    suspend fun update(page: Page)

    @Query("DELETE FROM pages WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM pages WHERE noteId = :noteId")
    suspend fun deleteByNote(noteId: Long)

    @Query("SELECT * FROM pages WHERE noteId = :noteId ORDER BY sortOrder ASC")
    fun getByNote(noteId: Long): Flow<List<Page>>

    @Query("SELECT * FROM pages WHERE noteId = :noteId ORDER BY sortOrder ASC")
    suspend fun getByNoteOnce(noteId: Long): List<Page>

    @Query("SELECT * FROM pages WHERE id = :id")
    fun getById(id: Long): Flow<Page?>

    @Query("SELECT * FROM pages WHERE id = :id")
    suspend fun getByIdOnce(id: Long): Page?

    @Query("SELECT COUNT(*) FROM pages WHERE noteId = :noteId")
    fun getCountByNote(noteId: Long): Flow<Int>

    @Query("UPDATE pages SET sortOrder = :newOrder WHERE id = :id")
    suspend fun updateSortOrder(id: Long, newOrder: Int)

    @Query("SELECT MAX(sortOrder) + 1 FROM pages WHERE noteId = :noteId")
    suspend fun getNextSortOrder(noteId: Long): Int?

    @Query("SELECT * FROM pages WHERE noteId = :noteId AND sortOrder = :sortOrder")
    suspend fun getByNoteAndOrder(noteId: Long, sortOrder: Int): Page?
}