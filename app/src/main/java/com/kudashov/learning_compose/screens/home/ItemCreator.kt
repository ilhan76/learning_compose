package com.kudashov.learning_compose.screens.home

import com.kudashov.learning_compose.screens.home.ui_data.TabData

object ItemCreator {

    fun getTabItems(): List<TabData> = listOf(
        TabData(
            id = "1",
            isNewFeature = false,
            title = "Editorial",
            isSelected = true
        ),
        TabData(
            id = "2",
            isNewFeature = true,
            title = "Random Photo",
            isSelected = false
        ),
        TabData(
            id = "3",
            isNewFeature = false,
            title = "3D Renders",
            isSelected = false
        )
    )
}