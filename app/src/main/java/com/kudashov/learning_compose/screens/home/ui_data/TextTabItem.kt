package com.kudashov.learning_compose.screens.home.ui_data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
sealed class TabItem : Parcelable {

    @Parcelize
    data class TextTabItem(
        val id: String,
        val isNewFeature: Boolean,
        val title: String,
        val isSelected: Boolean
    ) : TabItem()

    @Parcelize
    object Divider : TabItem()
}
