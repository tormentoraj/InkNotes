package com.inknotes.inknotes.di

import android.content.Context
import com.inknotes.inknotes.data.AppDatabase
import com.inknotes.inknotes.data.AppPreferences
import com.inknotes.inknotes.data.dao.FolderDao
import com.inknotes.inknotes.data.dao.NoteDao
import com.inknotes.inknotes.data.dao.PageDao
import com.inknotes.inknotes.data.dao.StrokeDao
import com.inknotes.inknotes.data.repo.FolderRepository
import com.inknotes.inknotes.data.repo.NoteRepository
import com.inknotes.inknotes.data.repo.StrokeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideFolderDao(database: AppDatabase): FolderDao {
        return database.folderDao()
    }

    @Provides
    @Singleton
    fun provideNoteDao(database: AppDatabase): NoteDao {
        return database.noteDao()
    }

    @Provides
    @Singleton
    fun providePageDao(database: AppDatabase): PageDao {
        return database.pageDao()
    }

    @Provides
    @Singleton
    fun provideStrokeDao(database: AppDatabase): StrokeDao {
        return database.strokeDao()
    }

    @Provides
    @Singleton
    fun provideFolderRepository(folderDao: FolderDao, noteDao: NoteDao): FolderRepository {
        return FolderRepository(folderDao, noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDao: NoteDao, pageDao: PageDao, strokeDao: StrokeDao): NoteRepository {
        return NoteRepository(noteDao, pageDao, strokeDao)
    }

    @Provides
    @Singleton
    fun provideStrokeRepository(strokeDao: StrokeDao): StrokeRepository {
        return StrokeRepository(strokeDao)
    }

    @Provides
    @Singleton
    fun provideAppPreferences(@ApplicationContext context: Context): AppPreferences {
        return AppPreferences(context)
    }
}