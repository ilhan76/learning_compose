package com.kudashov.learning_compose.screens.home

import android.os.Parcelable
import com.kudashov.learning_compose.domain.PhotoItem
import com.kudashov.learning_compose.screens.home.ui_data.TabData
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HomeState(
    val photos: List<PhotoItem> = emptyList(),
    val tabs: List<TabData> = emptyList()
): Parcelable