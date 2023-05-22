package com.kudashov.learning_compose.screens.home

import com.kudashov.learning_compose.screens.home.ui_data.TabItem


object ItemCreator {

    fun getTabItems(): List<TabItem> = listOf(
        TabItem.TextTabItem(
            id = "1",
            isNewFeature = false,
            title = "Editorial",
            isSelected = true
        ),
        TabItem.Divider,
        TabItem.TextTabItem(
            id = "2",
            isNewFeature = true,
            title = "Random Photo",
            isSelected = false
        ),
        TabItem.TextTabItem(
            id = "3",
            isNewFeature = false,
            title = "3D Renders",
            isSelected = false
        ),
        TabItem.TextTabItem(
            id = "4",
            isNewFeature = false,
            title = "Stub",
            isSelected = false
        ),
        TabItem.TextTabItem(
            id = "5",
            isNewFeature = false,
            title = "One more stub",
            isSelected = false
        )
    )
}