package com.inknotes.inknotes.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "pages",
    foreignKeys = [
        ForeignKey(
            entity = Note::class,
            parentColumns = ["id"],
            childColumns = ["noteId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["noteId"]),
        Index(value = ["sortOrder"])
    ]
)
data class Page(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val noteId: Long,
    val sortOrder: Int = 0,
    val backgroundColor: Int = 0xFFFFFFFF.toInt(),
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val thumbnailPath: String? = null,
    val width: Int = 0,
    val height: Int = 0
) {
    companion object {
        fun createNew(noteId: Long): Page {
            return Page(
                noteId = noteId,
                sortOrder = 0,
                createdAt = Date(),
                updatedAt = Date()
            )
        }
    }
}