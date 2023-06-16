package com.kudashov.learning_compose.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kudashov.learning_compose.base.storage.ThemeStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themeStorage: ThemeStorage
) : ViewModel() {

    var isDarkTheme by mutableStateOf(themeStorage.isDark)

    fun updateThemeValue(isDarkTheme: Boolean) {
        themeStorage.saveTheme(isDarkTheme)
        this.isDarkTheme = isDarkTheme
    }
}