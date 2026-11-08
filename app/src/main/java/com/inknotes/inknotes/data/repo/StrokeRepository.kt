package com.inknotes.inknotes.data.repo

import com.inknotes.inknotes.data.dao.StrokeDao
import com.inknotes.inknotes.data.model.Stroke
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StrokeRepository @Inject constructor(
    private val strokeDao: StrokeDao
) {
    fun getStrokesByPage(pageId: Long): Flow<List<Stroke>> = strokeDao.getByPage(pageId)

    suspend fun addStroke(stroke: Stroke): Long = strokeDao.insert(stroke)

    suspend fun addStrokes(strokes: List<Stroke>): List<Long> = strokeDao.insertAll(strokes)

    suspend fun updateStroke(stroke: Stroke) = strokeDao.update(stroke)

    suspend fun deleteStroke(id: Long) = strokeDao.delete(id)

    suspend fun deleteStrokesByPage(pageId: Long) = strokeDao.deleteByPage(pageId)

    suspend fun getStrokesByPageOnce(pageId: Long): List<Stroke> = strokeDao.getByPageOnce(pageId)

    suspend fun getNextSortOrder(pageId: Long): Int = strokeDao.getNextSortOrder(pageId) ?: 0

    suspend fun undoLastStroke(pageId: Long): Boolean {
        val strokes = strokeDao.getByPageOnce(pageId)
        if (strokes.isEmpty()) return false
        
        val lastStroke = strokes.maxByOrNull { it.sortOrder } ?: return false
        strokeDao.delete(lastStroke.id)
        return true
    }

    suspend fun redoStroke(pageId: Long, stroke: Stroke): Long {
        return strokeDao.insert(stroke)
    }

    suspend fun clearPage(pageId: Long) {
        strokeDao.deleteByPage(pageId)
    }
}