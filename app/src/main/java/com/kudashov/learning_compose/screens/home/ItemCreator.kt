package com.kudashov.learning_compose.screens.home

import com.kudashov.learning_compose.domain.Topic
import com.kudashov.learning_compose.screens.home.ui_data.TabItem

object ItemCreator {

    const val EDITORIAL_ID = "EDITORIAL"
    const val RANDOM_PHOTO_ID = "RANDOM_PHOTO"

    fun createTopics(topics: List<Topic>): List<TabItem> {
        val tmpList = listOf(
            TabItem.TextTabItem(
                id = EDITORIAL_ID,
                isNewFeature = false,
                title = "Editorial",
                isSelected = true
            ),
            TabItem.Divider,
            TabItem.TextTabItem(
                id = RANDOM_PHOTO_ID,
                isNewFeature = true,
                title = "Random Photo",
                isSelected = false
            ),
        )

        return listOf(
            tmpList,
            topics.map { it.toTopicUi(false) }
        ).flatten()
    }

    private fun Topic.toTopicUi(isNewFeature: Boolean): TabItem.TextTabItem {
        return TabItem.TextTabItem(
            id = id,
            title = title,
            isNewFeature = isNewFeature,
            isSelected = false
        )
    }
}