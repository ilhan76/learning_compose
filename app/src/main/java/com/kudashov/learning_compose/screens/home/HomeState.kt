package com.kudashov.learning_compose.screens.home

import android.os.Parcelable
import com.kudashov.learning_compose.domain.PhotoDetail
import com.kudashov.learning_compose.domain.PhotoItem
import com.kudashov.learning_compose.domain.util.LoadStatus
import com.kudashov.learning_compose.domain.util.LoadableData
import com.kudashov.learning_compose.screens.home.ui_data.TabItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HomeState(
    val photos: List<PhotoItem> = emptyList(),
    val tabs: List<TabItem> = emptyList(),
    val randomPhoto: LoadableData<PhotoDetail> = LoadableData(null, LoadStatus.Loading)
) : Parcelable {

    val selectedTopicId: String = tabs
        .filterIsInstance<TabItem.TextTabItem>()
        .find { it.isSelected }
        ?.id
        .orEmpty()
}