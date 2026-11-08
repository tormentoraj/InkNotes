package com.inknotes.inknotes.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "notes",
    foreignKeys = [
        ForeignKey(
            entity = Folder::class,
            parentColumns = ["id"],
            childColumns = ["folderId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["folderId"]),
        Index(value = ["title"]),
        Index(value = ["createdAt"]),
        Index(value = ["updatedAt"])
    ]
)
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val folderId: Long? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val isPinned: Boolean = false,
    val thumbnailPath: String? = null,
    val pageCount: Int = 1,
    val searchText: String? = null
) {
    companion object {
        fun createNew(folderId: Long? = null): Note {
            return Note(
                title = "Untitled Note",
                folderId = folderId,
                createdAt = Date(),
                updatedAt = Date()
            )
        }
    }
}