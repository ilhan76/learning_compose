package com.kudashov.learning_compose.screens.home.ui_data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TabData(
    val id: String,
    val isNewFeature: Boolean,
    val title: String,
    val isSelected: Boolean
) : Parcelable