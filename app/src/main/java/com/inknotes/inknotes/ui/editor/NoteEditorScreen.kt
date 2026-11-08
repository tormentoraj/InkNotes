package com.inknotes.inknotes.ui.editor

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Highlight
import androidx.compose.material.icons.filled.LineWeight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Redraw
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke as ComposeStroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.inknotes.inknotes.data.model.Stroke as DataStroke
import com.inknotes.inknotes.ui.components.ColorPicker
import com.inknotes.inknotes.ui.theme.HighlighterColors
import com.inknotes.inknotes.ui.theme.PenColors
import com.inknotes.inknotes.viewmodel.EditorViewModel
import com.inknotes.inknotes.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun NoteEditorScreen(
    navController: NavController,
    noteViewModel: NoteViewModel,
    noteId: Long = 0L
) {
    val editorViewModel: EditorViewModel = hiltViewModel()
    val currentPage by editorViewModel.currentPage.collectAsState()
    val strokes by editorViewModel.strokes.collectAsState()
    val currentTool by editorViewModel.currentTool.collectAsState()
    val strokeColor by editorViewModel.strokeColor.collectAsState()
    val strokeWidth by editorViewModel.strokeWidth.collectAsState()
    val zoomLevel by editorViewModel.zoomLevel.collectAsState()
    val offsetX by editorViewModel.offsetX.collectAsState()
    val offsetY by editorViewModel.offsetY.collectAsState()

    var showColorPicker by remember { mutableStateOf(false) }
    var showWidthSlider by remember { mutableStateOf(false) }
    var showMoreMenu by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(noteId) {
        if (noteId != 0L) {
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Note Editor") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { editorViewModel.undo() }) {
                        Icon(Icons.Default.Undo, contentDescription = "Undo")
                    }
                    IconButton(onClick = { editorViewModel.redo() }) {
                        Icon(Icons.Default.Redraw, contentDescription = "Redo")
                    }
                    IconButton(onClick = { showMoreMenu = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }

                    DropdownMenu(
                        expanded = showMoreMenu,
                        onDismissRequest = { showMoreMenu = false }
                    ) {
                        Text("Save", modifier = Modifier.padding(16.dp))
                        Text("Export as PDF", modifier = Modifier.padding(16.dp))
                        Text("Share", modifier = Modifier.padding(16.dp))
                        Text("Delete", modifier = Modifier.padding(16.dp))
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ToolButton(
                            icon = Icons.Default.Edit,
                            isSelected = currentTool == DataStroke.ToolType.PEN,
                            onClick = { editorViewModel.setCurrentTool(DataStroke.ToolType.PEN) }
                        )
                        ToolButton(
                            icon = Icons.Default.Highlight,
                            isSelected = currentTool == DataStroke.ToolType.MARKER,
                            onClick = { editorViewModel.setCurrentTool(DataStroke.ToolType.MARKER) }
                        )
                        ToolButton(
                            icon = Icons.Default.ContentCut,
                            isSelected = currentTool == DataStroke.ToolType.ERASER,
                            onClick = { editorViewModel.setCurrentTool(DataStroke.ToolType.ERASER) }
                        )
                        ToolButton(
                            icon = Icons.Default.TextFields,
                            isSelected = false,
                            onClick = { }
                        )

                        Spacer(Modifier.width(8.dp))

                        IconButton(onClick = { showColorPicker = true }) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color(strokeColor), RoundedCornerShape(8.dp))
                            )
                        }

                        IconButton(onClick = { showWidthSlider = true }) {
                            Icon(Icons.Default.LineWeight, contentDescription = "Width")
                        }

                        IconButton(onClick = { editorViewModel.setZoomLevel(zoomLevel * 1.2f) }) {
                            Icon(Icons.Default.ZoomIn, contentDescription = "Zoom In")
                        }
                        IconButton(onClick = { editorViewModel.setZoomLevel(zoomLevel / 1.2f) }) {
                            Icon(Icons.Default.ZoomOut, contentDescription = "Zoom Out")
                        }

                        IconButton(onClick = { editorViewModel.addNewPage() }) {
                            Icon(Icons.Default.Add, contentDescription = "New Page")
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            DrawingCanvas(
                strokes = strokes,
                currentTool = currentTool,
                currentColor = Color(strokeColor),
                currentWidth = strokeWidth,
                zoomLevel = zoomLevel,
                offsetX = offsetX,
                offsetY = offsetY,
                onStrokeAdded = { points, pressures, timestamps ->
                    editorViewModel.addStroke(points, pressures, timestamps)
                },
                onPan = { dx, dy ->
                    editorViewModel.setOffset(offsetX + dx, offsetY + dy)
                },
                modifier = Modifier.fillMaxSize()
            )

            if (showColorPicker) {
                ModalBottomSheet(
                    onDismissRequest = { showColorPicker = false }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("Select Color", style = MaterialTheme.typography.titleMedium)

                        val colors = if (currentTool == DataStroke.ToolType.MARKER) {
                            HighlighterColors
                        } else {
                            PenColors
                        }

                        ColorPicker(
                            colors = colors,
                            selectedColor = Color(strokeColor),
                            onColorSelected = { color ->
                                editorViewModel.setStrokeColor(color.value.toInt())
                                showColorPicker = false
                            }
                        )
                    }
                }
            }

            if (showWidthSlider) {
                ModalBottomSheet(
                    onDismissRequest = { showWidthSlider = false }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("Stroke Width", style = MaterialTheme.typography.titleMedium)
                        Text("${strokeWidth.dp}")
                        Slider(
                            value = strokeWidth,
                            onValueChange = { editorViewModel.setStrokeWidth(it) },
                            valueRange = 1f..20f,
                            steps = 19
                        )
                        Button(
                            onClick = { showWidthSlider = false },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Done")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ToolButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(40.dp)
            .background(
                if (isSelected) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.surfaceContainer
                },
                RoundedCornerShape(8.dp)
            )
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = if (isSelected) {
                MaterialTheme.colorScheme.onPrimaryContainer
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )
    }
}

@Composable
fun DrawingCanvas(
    strokes: List<DataStroke>,
    currentTool: DataStroke.ToolType,
    currentColor: Color,
    currentWidth: Float,
    zoomLevel: Float,
    offsetX: Float,
    offsetY: Float,
    onStrokeAdded: (List<Float>, List<Float>, List<Long>) -> Unit,
    onPan: (Float, Float) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentPath by remember { mutableStateOf(Path()) }
    var currentPoints by remember { mutableStateOf(listOf<Offset>()) }
    var currentPressures by remember { mutableStateOf(listOf<Float>()) }
    var currentTimestamps by remember { mutableStateOf(listOf<Long>()) }
    var isDrawing by remember { mutableStateOf(false) }

    val density = LocalDensity.current

    Canvas(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        if (currentTool != DataStroke.ToolType.ERASER) {
                            currentPath = Path()
                            currentPoints = listOf(offset)
                            currentPressures = listOf(1f)
                            currentTimestamps = listOf(System.currentTimeMillis())
                            currentPath.moveTo(offset.x, offset.y)
                            isDrawing = true
                        }
                    },
                    onDrag = { change, dragAmount ->
                        if (isDrawing) {
                            val newOffset = change.position
                            currentPoints = currentPoints + newOffset
                            currentPressures = currentPressures + 1f
                            currentTimestamps = currentTimestamps + System.currentTimeMillis()

                            val lastPoint = currentPoints[currentPoints.size - 2]
                            val controlPoint = Offset(
                                (lastPoint.x + newOffset.x) / 2,
                                (lastPoint.y + newOffset.y) / 2
                            )
                            currentPath.quadraticBezierTo(
                                controlPoint.x, controlPoint.y,
                                newOffset.x, newOffset.y
                            )
                        }
                        if (change.pressedCount() > 1) {
                            onPan(-dragAmount.x, -dragAmount.y)
                        }
                    },
                    onDragEnd = {
                        if (isDrawing && currentPoints.size > 1) {
                            val points = currentPoints.flatMap { offset ->
                                listOf(offset.x, offset.y)
                            }
                            onStrokeAdded(points, currentPressures, currentTimestamps)
                        }
                        isDrawing = false
                    },
                    onDragCancel = {
                        isDrawing = false
                    }
                )
            }
            .onSizeChanged { size ->
            }
    ) {
        translate(left = offsetX, top = offsetY) {
            scale(zoomLevel, zoomLevel) {
                strokes.forEach { stroke ->
                    val points = stroke.points.chunked(2).mapIndexed { index, pair ->
                        Offset(pair[0], pair[1])
                    }

                    if (points.size > 1) {
                        val path = Path().apply {
                            moveTo(points[0].x, points[0].y)
                            for (i in 1 until points.size) {
                                val prev = points[i - 1]
                                val curr = points[i]
                                quadraticBezierTo(
                                    (prev.x + curr.x) / 2,
                                    (prev.y + curr.y) / 2,
                                    curr.x,
                                    curr.y
                                )
                            }
                        }

                        val strokeStyle = when (stroke.toolType) {
                            DataStroke.ToolType.PEN -> ComposeStroke(
                                width = stroke.width * density.density,
                                cap = StrokeCap.Round
                            )
                            DataStroke.ToolType.MARKER -> ComposeStroke(
                                width = stroke.width * density.density,
                                cap = StrokeCap.Round
                            )
                            DataStroke.ToolType.ERASER -> ComposeStroke(
                                width = stroke.width * density.density,
                                cap = StrokeCap.Round
                            )
                            DataStroke.ToolType.HIGHLIGHTER -> ComposeStroke(
                                width = stroke.width * density.density,
                                cap = StrokeCap.Round
                            )
                        }

                        drawPath(
                            path = path,
                            color = Color(stroke.color),
                            style = strokeStyle
                        )
                    }
                }

                if (isDrawing && currentPath.isNotEmpty) {
                    drawPath(
                        path = currentPath,
                        color = currentColor,
                        style = ComposeStroke(
                            width = currentWidth * density.density,
                            cap = StrokeCap.Round
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun ColorPicker(
    colors: List<Color>,
    selectedColor: Color,
    onColorSelected: (Color) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        colors.forEach { color ->
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color)
                    .combinedClickable(
                        onClick = { onColorSelected(color) }
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (color == selectedColor) {
                    Icon(
                        Icons.Default.Done,
                        contentDescription = "Selected",
                        tint = Color.White
                    )
                }
            }
        }
    }
}