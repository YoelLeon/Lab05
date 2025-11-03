package com.joel.lab05

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Crea el DataStore como extensión del Context
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_preferences")

class ThemeManager(private val context: Context) {
    companion object {
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
    }

    // Obtener el estado del tema como Flow
    val isDarkTheme: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_DARK_THEME] ?: false // Por defecto tema claro
        }

    // Guardar la preferencia del tema
    suspend fun saveThemePreference(isDarkTheme: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_THEME] = isDarkTheme
        }
    }
}