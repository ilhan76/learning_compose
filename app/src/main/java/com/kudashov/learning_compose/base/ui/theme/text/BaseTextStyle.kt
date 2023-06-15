package com.kudashov.learning_compose.base.ui.theme.text

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

class BaseTextStyle(
    base: TextStyle,
    colorPalette: ProjectTextColorPalette
) {
    val text10: ColoredTextStyle = ColoredTextStyle(
        textStyle = base.copy(fontSize = 10.sp, lineHeight = 12.sp),
        colorPalette = colorPalette
    )
    val text14: ColoredTextStyle = ColoredTextStyle(
        textStyle = base.copy(fontSize = 14.sp, lineHeight = 18.sp),
        colorPalette = colorPalette
    )
    val text16: ColoredTextStyle = ColoredTextStyle(
        textStyle = base.copy(fontSize = 16.sp, lineHeight = 24.sp),
        colorPalette = colorPalette
    )
    val text24: ColoredTextStyle = ColoredTextStyle(
        textStyle = base.copy(fontSize = 24.sp, lineHeight = 32.sp),
        colorPalette = colorPalette
    )
}
