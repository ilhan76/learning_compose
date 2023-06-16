package com.kudashov.learning_compose.base.ui.theme.text

import com.kudashov.learning_compose.base.ui.theme.GreenTextColor
import com.kudashov.learning_compose.base.ui.theme.GreenTextColorDark
import com.kudashov.learning_compose.base.ui.theme.HintTextColor
import com.kudashov.learning_compose.base.ui.theme.HintTextColorDark
import com.kudashov.learning_compose.base.ui.theme.LightTextColor
import com.kudashov.learning_compose.base.ui.theme.LightTextColorDark
import com.kudashov.learning_compose.base.ui.theme.StandardTextColor
import com.kudashov.learning_compose.base.ui.theme.StandardTextColorDark

object TextTheme {

    object Light : ProjectTypography(
        ProjectTextColorPalette(
            standard = StandardTextColor,
            hint = HintTextColor,
            light = LightTextColor,
            accent = GreenTextColor
        )
    )

    object Dark : ProjectTypography(
        ProjectTextColorPalette(
            standard = StandardTextColorDark,
            hint = HintTextColorDark,
            light = LightTextColorDark,
            accent = GreenTextColorDark
        )
    )
}
