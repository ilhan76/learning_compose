package com.kudashov.learning_compose.base.ui.theme.text

import androidx.compose.ui.text.TextStyle

class ColoredTextStyle(
    textStyle: TextStyle,
    colorPalette: ProjectTextColorPalette
) {
    val standard = textStyle.copy(color = colorPalette.standard)
    val hint = textStyle.copy(color = colorPalette.hint)
    val light = textStyle.copy(color = colorPalette.light)
    val accent = textStyle.copy(color = colorPalette.accent)
}