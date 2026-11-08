package com.inknotes.inknotes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.inknotes.inknotes.data.dao.FolderDao
import com.inknotes.inknotes.data.dao.NoteDao
import com.inknotes.inknotes.data.dao.PageDao
import com.inknotes.inknotes.data.dao.StrokeDao
import com.inknotes.inknotes.data.model.Folder
import com.inknotes.inknotes.data.model.Note
import com.inknotes.inknotes.data.model.Page
import com.inknotes.inknotes.data.model.Stroke

@Database(
    entities = [Folder::class, Note::class, Page::class, Stroke::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun folderDao(): FolderDao
    abstract fun noteDao(): NoteDao
    abstract fun pageDao(): PageDao
    abstract fun strokeDao(): StrokeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "inknotes_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}