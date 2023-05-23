package com.kudashov.learning_compose.domain.util

import java.io.Serializable

data class LoadableData<T>(
    val data: T?,
    val loadStatus: LoadStatus
): Serializable {
    val isLoading get() = loadStatus == LoadStatus.Loading
}

enum class LoadStatus {
    Loading,
    Loaded,
    Error
}