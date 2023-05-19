package com.kudashov.learning_compose.screens.home

import com.kudashov.learning_compose.domain.PhotoItem

data class HomeState(
    val photos: List<PhotoItem> = emptyList()
)