package com.inknotes.inknotes.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.inknotes.inknotes.ui.components.BottomNavigationBar
import com.inknotes.inknotes.ui.components.NavigationRail
import com.inknotes.inknotes.ui.components.TwoPaneLayout
import com.inknotes.inknotes.ui.editor.NoteEditorScreen
import com.inknotes.inknotes.ui.screens.folders.FoldersScreen
import com.inknotes.inknotes.ui.screens.notes.NotesScreen
import com.inknotes.inknotes.ui.screens.settings.SettingsScreen
import com.inknotes.inknotes.viewmodel.NoteViewModel
import com.inknotes.inknotes.viewmodel.SettingsViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppScreen(windowSizeClass: WindowSizeClass) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val noteViewModel: NoteViewModel = hiltViewModel()
    
    val isCompact = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
    
    if (isCompact) {
        AnimatedContent(
            targetState = currentRoute,
            transitionSpec = {
                (fadeIn() + slideInHorizontally { width -> width / 2 }) togetherWith
                        (fadeOut() + slideOutHorizontally { width -> -width / 2 })
            },
            label = "Navigation Animation"
        ) { route ->
            when (route) {
                "notes" -> NotesScreen(navController, noteViewModel)
                "folders" -> FoldersScreen(navController)
                "settings" -> SettingsScreen(navController, settingsViewModel)
                "editor/{noteId}" -> NoteEditorScreen(navController, noteViewModel)
                else -> NotesScreen(navController, noteViewModel)
            }
        }
        
        BottomNavigationBar(
            currentRoute = currentRoute,
            onNavigate = { route ->
                navController.navigate(route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )
    } else {
        TwoPaneLayout(
            windowSizeClass = windowSizeClass,
            currentRoute = currentRoute,
            onNavigate = { route ->
                navController.navigate(route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            navController = navController,
            noteViewModel = noteViewModel,
            settingsViewModel = settingsViewModel
        )
        
        NavigationRail(
            currentRoute = currentRoute,
            onNavigate = { route ->
                navController.navigate(route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )
    }
}