package com.inknotes.inknotes.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.inknotes.inknotes.data.model.Note
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: Note): Long

    @Update
    suspend fun update(note: Note)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM notes ORDER BY isPinned DESC, updatedAt DESC")
    fun getAll(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE folderId = :folderId ORDER BY isPinned DESC, updatedAt DESC")
    fun getByFolder(folderId: Long): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE folderId IS NULL ORDER BY isPinned DESC, updatedAt DESC")
    fun getUnfiled(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getById(id: Long): Flow<Note?>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getByIdOnce(id: Long): Note?

    @Query("SELECT COUNT(*) FROM notes WHERE folderId = :folderId")
    fun getCountByFolder(folderId: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM notes")
    fun getTotalCount(): Flow<Int>

    @Query("SELECT * FROM notes WHERE title LIKE :query OR searchText LIKE :query ORDER BY isPinned DESC, updatedAt DESC")
    fun search(query: String): Flow<List<Note>>

    @Query("UPDATE notes SET folderId = :newFolderId WHERE id = :noteId")
    suspend fun moveToFolder(noteId: Long, newFolderId: Long?)

    @Query("UPDATE notes SET isPinned = :pinned WHERE id = :id")
    suspend fun setPinned(id: Long, pinned: Boolean)

    @Query("UPDATE notes SET updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateTimestamp(id: Long, updatedAt: Date)

    @Query("SELECT * FROM notes ORDER BY title ASC")
    fun getAllAlphabetical(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE folderId = :folderId ORDER BY title ASC")
    fun getByFolderAlphabetical(folderId: Long): Flow<List<Note>>
}