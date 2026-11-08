package com.inknotes.inknotes.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.inknotes.inknotes.data.model.Folder
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {
    @Insert
    suspend fun insert(folder: Folder): Long

    @Insert
    suspend fun insertAll(folders: List<Folder>): List<Long>

    @Update
    suspend fun update(folder: Folder)

    @Query("DELETE FROM folders WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM folders ORDER BY sortOrder ASC, name ASC")
    fun getAll(): Flow<List<Folder>>

    @Query("SELECT * FROM folders WHERE id = :id")
    fun getById(id: Long): Flow<Folder?>

    @Query("SELECT * FROM folders WHERE id = :id")
    suspend fun getByIdOnce(id: Long): Folder?

    @Query("SELECT COUNT(*) FROM folders")
    fun getCount(): Flow<Int>

    @Query("SELECT * FROM folders ORDER BY name ASC")
    fun getAllAlphabetical(): Flow<List<Folder>>

    @Query("UPDATE folders SET sortOrder = :newOrder WHERE id = :id")
    suspend fun updateSortOrder(id: Long, newOrder: Int)

    @Query("SELECT MAX(sortOrder) + 1 FROM folders")
    suspend fun getNextSortOrder(): Int?
}