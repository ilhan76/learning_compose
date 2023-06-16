package com.kudashov.learning_compose.base.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeStorage @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    private val key = "THEME_TYPE"

    val isDark get() = sharedPreferences.getBoolean(key, false)

    fun saveTheme(isDark: Boolean) = sharedPreferences.edit {
        putBoolean(key, isDark)
    }
}