package com.kudashov.learning_compose.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kudashov.learning_compose.base.storage.ThemeStorage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeManager @Inject constructor(
    private val themeStorage: ThemeStorage
) : ViewModel() {

    var isDarkTheme: MutableState<Boolean> = mutableStateOf(themeStorage.isDark)


    fun updateThemeValue(isDarkTheme: Boolean) {
        themeStorage.saveTheme(isDarkTheme)
        this.isDarkTheme.value = isDarkTheme
    }
}