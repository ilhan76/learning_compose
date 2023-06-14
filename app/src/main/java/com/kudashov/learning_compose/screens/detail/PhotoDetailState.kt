package com.kudashov.learning_compose.screens.detail

import com.kudashov.learning_compose.base.domain.PhotoDetail
import com.kudashov.learning_compose.base.domain.PhotoStatistics

data class PhotoDetailState(
    val photoDetail: PhotoDetail? = null,
    val photoStatistics: PhotoStatistics? = null
)
