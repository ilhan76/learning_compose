package com.kudashov.learning_compose.ui.theme

import androidx.compose.ui.graphics.Color

//region Theme
val LightGreen = Color(0xFF30C150)
val SuperLightGreen = Color(0xFF42DB63)

val White = Color(0xFFFFFFFF)
val DarkGrey = Color(0xFF878787)
val Grey = Color(0xFFC4C4C4)
val LightGrey = Color(0xFFF2F2F2)

val Dark = Color(0xFF251F26)
val Black = Color(0xFF000000)
//endregion

//region Text (White theme)
val StandardTextColor = Dark
val HintTextColor = Dark.copy(alpha = 0.64f)
val LightTextColor = Dark.copy(alpha = 0.32f)
//endregion

//region Text (Black theme)
val StandardTextColorBlack : Color = White.copy(alpha = 0.8f)
val HintTextColorBlack = White.copy(alpha = 0.64f)
val LightTextColorBlack = White.copy(alpha = 0.32f)
//endregion
