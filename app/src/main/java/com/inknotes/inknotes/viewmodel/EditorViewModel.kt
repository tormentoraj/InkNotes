package com.inknotes.inknotes.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inknotes.inknotes.data.model.Page
import com.inknotes.inknotes.data.model.Stroke
import com.inknotes.inknotes.data.repo.NoteRepository
import com.inknotes.inknotes.data.repo.StrokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val strokeRepository: StrokeRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val noteId: Long = savedStateHandle.get<Long>("noteId") ?: 0L
    private val pageId: Long = savedStateHandle.get<Long>("pageId") ?: 0L

    private val _currentPage = MutableStateFlow<Page?>(null)
    val currentPage: StateFlow<Page?> = _currentPage.asStateFlow()

    private val _strokes = MutableStateFlow<List<Stroke>>(emptyList())
    val strokes: StateFlow<List<Stroke>> = _strokes.asStateFlow()

    private val _currentTool = MutableStateFlow<Stroke.ToolType>(Stroke.ToolType.PEN)
    val currentTool: StateFlow<Stroke.ToolType> = _currentTool.asStateFlow()

    private val _strokeColor = MutableStateFlow<Int>(0xFF000000.toInt())
    val strokeColor: StateFlow<Int> = _strokeColor.asStateFlow()

    private val _strokeWidth = MutableStateFlow<Float>(3f)
    val strokeWidth: StateFlow<Float> = _strokeWidth.asStateFlow()

    private val _zoomLevel = MutableStateFlow<Float>(1f)
    val zoomLevel: StateFlow<Float> = _zoomLevel.asStateFlow()

    private val _offsetX = MutableStateFlow<Float>(0f)
    val offsetX: StateFlow<Float> = _offsetX.asStateFlow()

    private val _offsetY = MutableStateFlow<Float>(0f)
    val offsetY: StateFlow<Float> = _offsetY.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val (note, pages) = noteRepository.getNoteWithPages(noteId)
            if (note != null && pages.isNotEmpty()) {
                val targetPage = if (pageId != 0L) {
                    pages.firstOrNull { it.id == pageId } ?: pages[0]
                } else {
                    pages[0]
                }
                _currentPage.value = targetPage
                val (page, strokes) = noteRepository.getPageWithStrokes(targetPage.id)
                _strokes.value = strokes
            }
        }
    }

    fun setCurrentTool(tool: Stroke.ToolType) {
        _currentTool.value = tool
    }

    fun setStrokeColor(color: Int) {
        _strokeColor.value = color
    }

    fun setStrokeWidth(width: Float) {
        _strokeWidth.value = width
    }

    fun setZoomLevel(zoom: Float) {
        _zoomLevel.value = zoom.coerceIn(0.5f, 4f)
    }

    fun setOffset(x: Float, y: Float) {
        _offsetX.value = x
        _offsetY.value = y
    }

    fun addStroke(points: List<Float>, pressures: List<Float>, timestamps: List<Long>) {
        val currentPage = _currentPage.value ?: return
        val stroke = Stroke(
            pageId = currentPage.id,
            points = points,
            pressures = pressures,
            timestamps = timestamps,
            color = _strokeColor.value,
            width = _strokeWidth.value,
            toolType = _currentTool.value,
            sortOrder = _strokes.value.size
        )
        
        viewModelScope.launch {
            val id = strokeRepository.addStroke(stroke)
            _strokes.value = _strokes.value + stroke.copy(id = id)
        }
    }

    fun undo() {
        if (_strokes.value.isEmpty()) return
        val lastStroke = _strokes.value.last()
        viewModelScope.launch {
            strokeRepository.deleteStroke(lastStroke.id)
            _strokes.value = _strokes.value.dropLast(1)
        }
    }

    fun redo() {
    }

    fun clearPage() {
        val currentPage = _currentPage.value ?: return
        viewModelScope.launch {
            strokeRepository.clearPage(currentPage.id)
            _strokes.value = emptyList()
        }
    }

    fun addNewPage() {
        viewModelScope.launch {
            val newPage = noteRepository.addPage(noteId)
            _currentPage.value = newPage
            _strokes.value = emptyList()
        }
    }

    fun switchToPage(page: Page) {
        viewModelScope.launch {
            val (_, strokes) = noteRepository.getPageWithStrokes(page.id)
            _currentPage.value = page
            _strokes.value = strokes
        }
    }

    fun deleteCurrentPage() {
        val currentPage = _currentPage.value ?: return
        viewModelScope.launch {
            noteRepository.deletePage(currentPage.id)
            val (_, pages) = noteRepository.getNoteWithPages(noteId)
            if (pages.isNotEmpty()) {
                val (page, strokes) = noteRepository.getPageWithStrokes(pages[0].id)
                _currentPage.value = page
                _strokes.value = strokes
            } else {
                val newPage = noteRepository.addPage(noteId)
                _currentPage.value = newPage
                _strokes.value = emptyList()
            }
        }
    }

    fun saveThumbnail(bitmap: Bitmap) {
    }
}
