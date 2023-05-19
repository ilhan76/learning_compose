package com.kudashov.learning_compose.ui.style

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kudashov.learning_compose.R
import com.kudashov.learning_compose.ui.theme.HintTextColor
import com.kudashov.learning_compose.ui.theme.LightGreen
import com.kudashov.learning_compose.ui.theme.LightTextColor
import com.kudashov.learning_compose.ui.theme.StandardTextColor

object ProjectTextStyle {
    private val BaseText = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.amiko_regular, FontWeight.Normal),
            Font(R.font.amiko_bold, FontWeight.Bold),
            Font(R.font.amiko_semi_bold, FontWeight.SemiBold)
        )
    )

    private val Regular = BaseText.copy(fontWeight = FontWeight.Normal)
    private val Bold = BaseText.copy(fontWeight = FontWeight.Bold)
    private val SemiBold = BaseText.copy(fontWeight = FontWeight.SemiBold)

    private val RegularText10 = Regular.copy(fontSize = 10.sp)
    private val RegularText14 = Regular.copy(fontSize = 14.sp)
    private val RegularText16 = Regular.copy(fontSize = 16.sp)
    private val RegularText18 = Regular.copy(fontSize = 18.sp)
    private val RegularText24 = Regular.copy(fontSize = 24.sp)

    val RegularText10Hint = RegularText10.copy(color = HintTextColor)

    val RegularText14Light = RegularText14.copy(color = LightTextColor)
    val RegularText14Green = RegularText14.copy(color = LightGreen)

    val RegularText16Green = RegularText16.copy(color = LightGreen)
    val RegularText16Light = RegularText16.copy(color = LightTextColor)

    val RegularText18Black = RegularText18.copy(color = StandardTextColor)
    val RegularText18Green = RegularText18.copy(color = LightGreen)

    val RegularText24Black = RegularText24.copy(color = StandardTextColor)
}
