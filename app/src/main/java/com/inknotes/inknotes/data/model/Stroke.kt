package com.inknotes.inknotes.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "strokes",
    foreignKeys = [
        ForeignKey(
            entity = Page::class,
            parentColumns = ["id"],
            childColumns = ["pageId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["pageId"]),
        Index(value = ["sortOrder"])
    ]
)
data class Stroke(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val pageId: Long,
    val points: List<Float> = emptyList(),
    val pressures: List<Float> = emptyList(),
    val timestamps: List<Long> = emptyList(),
    val color: Int = 0xFF000000.toInt(),
    val width: Float = 3f,
    val toolType: ToolType = ToolType.PEN,
    val sortOrder: Int = 0
) {
    enum class ToolType {
        PEN, MARKER, ERASER, HIGHLIGHTER
    }

    companion object {
        fun createPenStroke(
            pageId: Long,
            points: List<Float>,
            color: Int = 0xFF000000.toInt(),
            width: Float = 3f
        ): Stroke {
            return Stroke(
                pageId = pageId,
                points = points,
                color = color,
                width = width,
                toolType = ToolType.PEN
            )
        }

        fun createMarkerStroke(
            pageId: Long,
            points: List<Float>,
            color: Int = 0x80FF0000.toInt(),
            width: Float = 8f
        ): Stroke {
            return Stroke(
                pageId = pageId,
                points = points,
                color = color,
                width = width,
                toolType = ToolType.MARKER
            )
        }

        fun createEraserStroke(
            pageId: Long,
            points: List<Float>,
            width: Float = 10f
        ): Stroke {
            return Stroke(
                pageId = pageId,
                points = points,
                color = 0xFFFFFFFF.toInt(),
                width = width,
                toolType = ToolType.ERASER
            )
        }
    }
}