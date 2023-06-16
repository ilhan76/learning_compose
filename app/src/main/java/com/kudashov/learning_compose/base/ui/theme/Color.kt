package com.kudashov.learning_compose.base.ui.theme

import androidx.compose.ui.graphics.Color

//region Theme
val LightGreen = Color(0xFF30C150)
val SuperLightGreen = Color(0xFF42DB63)

val White = Color(0xFFFFFFFF)
val DarkGrey = Color(0xFF989898)
val Grey = Color(0xFFC4C4C4)
val LightGrey = Color(0xFFF2F2F2)

val Dark = Color(0xFF251F26)
val Black = Color(0xFF000000)
//endregion

//region Text (White theme)
val StandardTextColor = Dark
val HintTextColor = Dark.copy(alpha = 0.64f)
val LightTextColor = Dark.copy(alpha = 0.32f)
val GreenTextColor = SuperLightGreen
//endregion

//region Text (Black theme)
val StandardTextColorDark : Color = White.copy(alpha = 0.8f)
val HintTextColorDark = White.copy(alpha = 0.64f)
val LightTextColorDark = White.copy(alpha = 0.32f)
val GreenTextColorDark = LightGreen
//endregion

// region Switch (Light theme)

// endregion

// region Switch (Dark theme)

// endregion