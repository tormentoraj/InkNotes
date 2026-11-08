package com.inknotes.inknotes.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.inknotes.inknotes.viewmodel.SettingsViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "inknotes_preferences")

class AppPreferences(private val context: Context) {
    private val dataStore = context.dataStore

    companion object {
        private val THEME_MODE = stringPreferencesKey("theme_mode")
        private val USE_DYNAMIC_COLORS = booleanPreferencesKey("use_dynamic_colors")
        private val DEFAULT_PEN_COLOR = intPreferencesKey("default_pen_color")
        private val DEFAULT_PEN_WIDTH = floatPreferencesKey("default_pen_width")
        private val SHOW_ONBOARDING = booleanPreferencesKey("show_onboarding")
    }

    val themeMode: Flow<SettingsViewModel.ThemeMode> = dataStore.data
        .map { preferences ->
            preferences[THEME_MODE]?.let { mode ->
                try {
                    SettingsViewModel.ThemeMode.valueOf(mode)
                } catch (e: IllegalArgumentException) {
                    SettingsViewModel.ThemeMode.SYSTEM
                }
            } ?: SettingsViewModel.ThemeMode.SYSTEM
        }

    val useDynamicColors: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[USE_DYNAMIC_COLORS] ?: true
        }

    val defaultPenColor: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[DEFAULT_PEN_COLOR] ?: 0xFF000000.toInt()
        }

    val defaultPenWidth: Flow<Float> = dataStore.data
        .map { preferences ->
            preferences[DEFAULT_PEN_WIDTH] ?: 3f
        }

    val showOnboarding: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[SHOW_ONBOARDING] ?: true
        }

    suspend fun setThemeMode(mode: SettingsViewModel.ThemeMode) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE] = mode.name
        }
    }

    suspend fun setUseDynamicColors(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[USE_DYNAMIC_COLORS] = enabled
        }
    }

    suspend fun setDefaultPenColor(color: Int) {
        dataStore.edit { preferences ->
            preferences[DEFAULT_PEN_COLOR] = color
        }
    }

    suspend fun setDefaultPenWidth(width: Float) {
        dataStore.edit { preferences ->
            preferences[DEFAULT_PEN_WIDTH] = width
        }
    }

    suspend fun setShowOnboarding(show: Boolean) {
        dataStore.edit { preferences ->
            preferences[SHOW_ONBOARDING] = show
        }
    }
}