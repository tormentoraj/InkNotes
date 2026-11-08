package com.inknotes.inknotes.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.inknotes.inknotes.data.repo.NoteRepository
import com.inknotes.inknotes.data.repo.StrokeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O])
class NoteViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var noteViewModel: NoteViewModel
    private val noteRepository: NoteRepository = mock()
    private val strokeRepository: StrokeRepository = mock()
    private val savedStateHandle = SavedStateHandle()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        noteViewModel = NoteViewModel(noteRepository, strokeRepository, savedStateHandle)
    }

    @Test
    fun testCreateNote() = runTest {
        val noteId = noteViewModel.createNote()
        assert(noteId != null)
    }

    @Test
    fun testSetCurrentFolder() = runTest {
        noteViewModel.setCurrentFolder(1L)
        assert(noteViewModel.searchQuery.value == "")
    }

    @Test
    fun testSetSearchQuery() = runTest {
        noteViewModel.setSearchQuery("test")
        assert(noteViewModel.searchQuery.value == "test")
    }
}