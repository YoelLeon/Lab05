package com.joel.lab05

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ThemeViewModel(private val themeManager: ThemeManager) : ViewModel() {

    // Exponemos el estado del tema como un Flow
    val isDarkTheme: Flow<Boolean> = themeManager.isDarkTheme

    // Función para cambiar el tema y guardarlo
    fun setDarkTheme(isDark: Boolean) {
        viewModelScope.launch {
            themeManager.saveThemePreference(isDark)
        }
    }
}

// Factory para crear el ThemeViewModel
class ThemeViewModelFactory(private val themeManager: ThemeManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ThemeViewModel(themeManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}