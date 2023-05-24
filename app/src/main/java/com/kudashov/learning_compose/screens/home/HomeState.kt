package com.kudashov.learning_compose.screens.home

import android.os.Parcelable
import com.kudashov.learning_compose.base.domain.PhotoDetail
import com.kudashov.learning_compose.base.domain.PhotoItem
import com.kudashov.learning_compose.base.domain.util.LoadStatus
import com.kudashov.learning_compose.base.domain.util.LoadableData
import com.kudashov.learning_compose.base.paging.PagerLoadStatus
import com.kudashov.learning_compose.screens.home.ui_data.TabItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HomeState(
    val loadStatus: PagerLoadStatus = PagerLoadStatus.Loaded,
    val photos: List<PhotoItem> = emptyList(),
    val page: Int = 1,
    val tabs: List<TabItem> = emptyList(),
    val randomPhoto: LoadableData<PhotoDetail> = LoadableData(null, LoadStatus.Loading)
) : Parcelable {

    val selectedTopicId: String = tabs
        .filterIsInstance<TabItem.TextTabItem>()
        .find { it.isSelected }
        ?.id
        .orEmpty()
}