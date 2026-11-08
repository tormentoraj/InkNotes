package com.inknotes.inknotes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "folders")
data class Folder(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val color: Int,
    val icon: String? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val sortOrder: Int = 0
) {
    companion object {
        val DEFAULT_COLORS = listOf(
            0xFFB388FF.toInt(),
            0xFF8AB4F8.toInt(),
            0xFF81C9FA.toInt(),
            0xFF8CD583.toInt(),
            0xFFFFB470.toInt(),
            0xFFFF9899.toInt(),
            0xFFFFD681.toInt(),
            0xFFFF8A85.toInt(),
        )

        val DEFAULT_ICONS = listOf(
            "math", "physics", "chemistry", "biology", "history", 
            "languages", "literature", "art", "music", "geography",
            "computer_science", "economics", "philosophy", "psychology"
        )

        val UNFILED_FOLDER_ID = -1L
        val UNFILED_FOLDER_NAME = "Unfiled"

        fun createUnfiledFolder(): Folder {
            return Folder(
                id = UNFILED_FOLDER_ID,
                name = UNFILED_FOLDER_NAME,
                color = 0xFF808080.toInt(),
                icon = null,
                createdAt = Date(),
                updatedAt = Date(),
                sortOrder = Int.MAX_VALUE
            )
        }

        fun createDefaultFolders(): List<Folder> {
            return listOf(
                Folder(name = "Math", color = 0xFFB388FF.toInt(), icon = "math", sortOrder = 0),
                Folder(name = "Physics", color = 0xFF8AB4F8.toInt(), icon = "physics", sortOrder = 1),
                Folder(name = "Chemistry", color = 0xFF81C9FA.toInt(), icon = "chemistry", sortOrder = 2),
                Folder(name = "Biology", color = 0xFF8CD583.toInt(), icon = "biology", sortOrder = 3),
                Folder(name = "History", color = 0xFFFFB470.toInt(), icon = "history", sortOrder = 4),
                Folder(name = "Languages", color = 0xFFFF9899.toInt(), icon = "languages", sortOrder = 5),
                Folder(name = "Literature", color = 0xFFFFD681.toInt(), icon = "literature", sortOrder = 6),
                Folder(name = "Notes", color = 0xFFFF8A85.toInt(), icon = null, sortOrder = 7),
            )
        }
    }
}