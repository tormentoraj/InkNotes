package com.inknotes.inknotes.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.WindowSizeClass
import com.inknotes.inknotes.ui.editor.NoteEditorScreen
import com.inknotes.inknotes.ui.screens.folders.FoldersScreen
import com.inknotes.inknotes.ui.screens.notes.NotesScreen
import com.inknotes.inknotes.ui.screens.settings.SettingsScreen
import com.inknotes.inknotes.viewmodel.NoteViewModel
import com.inknotes.inknotes.viewmodel.SettingsViewModel

@Composable
fun TwoPaneLayout(
    windowSizeClass: WindowSizeClass,
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    navController: androidx.navigation.NavHostController,
    noteViewModel: NoteViewModel,
    settingsViewModel: SettingsViewModel
) {
    val isMediumWidth = windowSizeClass.widthSizeClass == androidx.window.layout.WindowWidthSizeClass.Medium
    val isExpandedWidth = windowSizeClass.widthSizeClass == androidx.window.layout.WindowWidthSizeClass.Expanded
    
    val paneWidth = if (isMediumWidth) 320.dp else 360.dp
    
    Row(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.width(paneWidth).fillMaxSize()) {
            when (currentRoute) {
                "notes" -> NotesScreen(navController, noteViewModel, isTwoPane = true)
                "folders" -> FoldersScreen(navController, isTwoPane = true)
                "settings" -> SettingsScreen(navController, settingsViewModel, isTwoPane = true)
                else -> NotesScreen(navController, noteViewModel, isTwoPane = true)
            }
        }
        
        Box(modifier = Modifier.fillMaxSize()) {
            when (currentRoute) {
                "editor/{noteId}" -> NoteEditorScreen(navController, noteViewModel)
                else -> NoteEditorScreen(navController, noteViewModel)
            }
        }
    }
}