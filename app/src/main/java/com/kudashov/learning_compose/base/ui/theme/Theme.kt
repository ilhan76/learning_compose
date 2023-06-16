package com.kudashov.learning_compose.base.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import com.kudashov.learning_compose.base.ui.theme.text.ProjectTypography
import com.kudashov.learning_compose.base.ui.theme.text.TextTheme

private val DarkColorScheme = darkColorScheme(
    primary = Dark,
    secondary = Black,
    tertiary = SuperLightGreen,
    onPrimary = White
)

private val LightColorScheme = lightColorScheme(
    primary = White,
    secondary = LightGrey,
    tertiary = LightGreen,
    onPrimary = Dark
)

val LocaleTypography = staticCompositionLocalOf<ProjectTypography> {
    error("No typography provided")
}

val LocaleColors = staticCompositionLocalOf<ColorScheme> {
    error("No colors provided")
}

object Theme {

    val colorScheme: ColorScheme
        @Composable
        get() = LocaleColors.current

    val typography: ProjectTypography
        @Composable
        get() = LocaleTypography.current
}

@Composable
fun LearningComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    CompositionLocalProvider(
        LocaleColors provides colorScheme,
        LocaleTypography provides if (darkTheme) TextTheme.Dark else TextTheme.Light,
        content = content
    )
}