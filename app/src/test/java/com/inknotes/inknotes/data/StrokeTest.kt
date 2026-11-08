package com.inknotes.inknotes.data

import com.inknotes.inknotes.data.model.Stroke
import org.junit.Assert.assertEquals
import org.junit.Test

class StrokeTest {

    @Test
    fun testCreatePenStroke() {
        val stroke = Stroke.createPenStroke(
            pageId = 1L,
            points = listOf(0f, 0f, 10f, 10f, 20f, 20f),
            color = 0xFF000000.toInt(),
            width = 3f
        )
        
        assertEquals(1L, stroke.pageId)
        assertEquals(listOf(0f, 0f, 10f, 10f, 20f, 20f), stroke.points)
        assertEquals(0xFF000000.toInt(), stroke.color)
        assertEquals(3f, stroke.width, 0.001f)
        assertEquals(Stroke.ToolType.PEN, stroke.toolType)
    }

    @Test
    fun testCreateMarkerStroke() {
        val stroke = Stroke.createMarkerStroke(
            pageId = 2L,
            points = listOf(0f, 0f, 10f, 10f),
            color = 0x80FF0000.toInt(),
            width = 8f
        )
        
        assertEquals(2L, stroke.pageId)
        assertEquals(Stroke.ToolType.MARKER, stroke.toolType)
        assertEquals(0x80FF0000.toInt(), stroke.color)
        assertEquals(8f, stroke.width, 0.001f)
    }

    @Test
    fun testCreateEraserStroke() {
        val stroke = Stroke.createEraserStroke(
            pageId = 3L,
            points = listOf(5f, 5f, 15f, 15f),
            width = 10f
        )
        
        assertEquals(3L, stroke.pageId)
        assertEquals(Stroke.ToolType.ERASER, stroke.toolType)
        assertEquals(0xFFFFFFFF.toInt(), stroke.color)
        assertEquals(10f, stroke.width, 0.001f)
    }
}