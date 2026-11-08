package com.inknotes.inknotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inknotes.inknotes.data.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferences: AppPreferences
) : ViewModel() {

    val themeMode: StateFlow<ThemeMode> = preferences.themeMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ThemeMode.SYSTEM)

    val useDynamicColors: StateFlow<Boolean> = preferences.useDynamicColors
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val defaultPenColor: StateFlow<Int> = preferences.defaultPenColor
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0xFF000000.toInt())

    val defaultPenWidth: StateFlow<Float> = preferences.defaultPenWidth
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 3f)

    val showOnboarding: StateFlow<Boolean> = preferences.showOnboarding
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch {
            preferences.setThemeMode(mode)
        }
    }

    fun setUseDynamicColors(enabled: Boolean) {
        viewModelScope.launch {
            preferences.setUseDynamicColors(enabled)
        }
    }

    fun setDefaultPenColor(color: Int) {
        viewModelScope.launch {
            preferences.setDefaultPenColor(color)
        }
    }

    fun setDefaultPenWidth(width: Float) {
        viewModelScope.launch {
            preferences.setDefaultPenWidth(width)
        }
    }

    fun setShowOnboarding(show: Boolean) {
        viewModelScope.launch {
            preferences.setShowOnboarding(show)
        }
    }

    enum class ThemeMode {
        SYSTEM, LIGHT, DARK
    }
}
