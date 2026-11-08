package com.inknotes.inknotes.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.inknotes.inknotes.data.model.Stroke
import kotlinx.coroutines.flow.Flow

@Dao
interface StrokeDao {
    @Insert
    suspend fun insert(stroke: Stroke): Long

    @Insert
    suspend fun insertAll(strokes: List<Stroke>): List<Long>

    @Update
    suspend fun update(stroke: Stroke)

    @Query("DELETE FROM strokes WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM strokes WHERE pageId = :pageId")
    suspend fun deleteByPage(pageId: Long)

    @Query("SELECT * FROM strokes WHERE pageId = :pageId ORDER BY sortOrder ASC")
    fun getByPage(pageId: Long): Flow<List<Stroke>>

    @Query("SELECT * FROM strokes WHERE pageId = :pageId ORDER BY sortOrder ASC")
    suspend fun getByPageOnce(pageId: Long): List<Stroke>

    @Query("SELECT * FROM strokes WHERE id = :id")
    suspend fun getByIdOnce(id: Long): Stroke?

    @Query("SELECT COUNT(*) FROM strokes WHERE pageId = :pageId")
    fun getCountByPage(pageId: Long): Flow<Int>

    @Query("UPDATE strokes SET sortOrder = :newOrder WHERE id = :id")
    suspend fun updateSortOrder(id: Long, newOrder: Int)

    @Query("SELECT MAX(sortOrder) + 1 FROM strokes WHERE pageId = :pageId")
    suspend fun getNextSortOrder(pageId: Long): Int?

    @Query("DELETE FROM strokes WHERE pageId = :pageId AND sortOrder >= :startOrder")
    suspend fun deleteFromOrder(pageId: Long, startOrder: Int)

    @Query("SELECT * FROM strokes WHERE pageId = :pageId AND sortOrder >= :startOrder ORDER BY sortOrder ASC")
    suspend fun getFromOrder(pageId: Long, startOrder: Int): List<Stroke>
}