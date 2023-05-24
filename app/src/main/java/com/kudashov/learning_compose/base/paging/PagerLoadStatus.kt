package com.kudashov.learning_compose.base.paging

enum class PagerLoadStatus {
    MainLoading,
    PullRefreshLoading,
    AppendLoading,
    Loaded,
    Error
}

fun PagerLoadStatus.isLoading(): Boolean =
    this != PagerLoadStatus.Loaded && this != PagerLoadStatus.Error