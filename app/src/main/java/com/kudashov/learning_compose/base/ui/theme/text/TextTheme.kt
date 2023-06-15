package com.kudashov.learning_compose.base.ui.theme.text

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.kudashov.learning_compose.base.ui.theme.GreenTextColor
import com.kudashov.learning_compose.base.ui.theme.GreenTextColorDark
import com.kudashov.learning_compose.base.ui.theme.HintTextColor
import com.kudashov.learning_compose.base.ui.theme.HintTextColorDark
import com.kudashov.learning_compose.base.ui.theme.LightTextColor
import com.kudashov.learning_compose.base.ui.theme.LightTextColorDark
import com.kudashov.learning_compose.base.ui.theme.StandardTextColor
import com.kudashov.learning_compose.base.ui.theme.StandardTextColorDark

object TextTheme {

    object Light : ProjectTextStyle(
        ProjectTextColorPalette(
            standard = StandardTextColor,
            hint = HintTextColor,
            light = LightTextColor,
            accent = GreenTextColor
        )
    )

    object Dark : ProjectTextStyle(
        ProjectTextColorPalette(
            standard = StandardTextColorDark,
            hint = HintTextColorDark,
            light = LightTextColorDark,
            accent = GreenTextColorDark
        )
    )

    val currentTheme
        @Composable
        get() = if (isSystemInDarkTheme()) Dark else Light
}
