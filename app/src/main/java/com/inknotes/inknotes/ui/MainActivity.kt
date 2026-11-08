package com.inknotes.inknotes.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.window.layout.WindowSizeClass
import androidx.window.layout.calculateWindowSizeClass
import com.inknotes.inknotes.ui.screens.AppScreen
import com.inknotes.inknotes.ui.theme.InkNotesTheme
import com.inknotes.inknotes.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val windowSizeClass = calculateWindowSizeClass(this)
            
            InkNotesTheme(
                themeMode = settingsViewModel.themeMode.value,
                useDynamicColors = settingsViewModel.useDynamicColors.value
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppScreen(windowSizeClass = windowSizeClass)
                }
            }
        }
    }
}