package com.kudashov.learning_compose.base.ui.theme.text

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.kudashov.learning_compose.R

private val AmikoFontFamily = FontFamily(
    Font(R.font.amiko_regular, FontWeight.Normal),
    Font(R.font.amiko_semi_bold, FontWeight.SemiBold),
    Font(R.font.amiko_bold, FontWeight.Bold)
)

abstract class ProjectTypography(colorPalette: ProjectTextColorPalette) {

    val regular = BaseTextStyle(
        TextStyle(
            fontFamily = AmikoFontFamily,
            fontWeight = FontWeight.Normal
        ),
        colorPalette = colorPalette
    )

    val semiBold = BaseTextStyle(
        TextStyle(
            fontFamily = AmikoFontFamily,
            fontWeight = FontWeight.SemiBold
        ),
        colorPalette = colorPalette
    )
}