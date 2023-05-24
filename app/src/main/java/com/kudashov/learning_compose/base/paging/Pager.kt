package com.kudashov.learning_compose.base.paging

import kotlinx.coroutines.delay

interface Pager<Key, Item> {
    suspend fun loadItems(loadDataType: LoadDataType)
}

class DefaultPager<Key, Item>(
    private val initialKey: Key,
    private inline val onLoadUpdated: (PagerLoadStatus) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> Result<List<Item>>,
    private inline val getNextKey: suspend (List<Item>) -> Key,
    private inline val onError: suspend (Throwable?) -> Unit,
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit
) : Pager<Key, Item> {

    private var currentKey = initialKey
    private var isLoading = false

    override suspend fun loadItems(loadDataType: LoadDataType) {
        if (isLoading) return
        if (loadDataType == LoadDataType.Append) currentKey = initialKey
        isLoading = true
        onLoadUpdated(
            when (loadDataType) {
                LoadDataType.PullRefresh -> PagerLoadStatus.PullRefreshLoading
                LoadDataType.MainRefresh -> PagerLoadStatus.MainLoading
                LoadDataType.Append -> PagerLoadStatus.AppendLoading
            }
        )

        delay(2000)
        val result = onRequest(currentKey)
        isLoading = false
        val items = result.getOrElse {
            onError(it)
            onLoadUpdated(PagerLoadStatus.Error)
            return
        }
        currentKey = getNextKey(items)
        onSuccess(items, currentKey)
        onLoadUpdated(PagerLoadStatus.Loaded)
    }
}

enum class PagerLoadStatus {
    MainLoading,
    PullRefreshLoading,
    AppendLoading,
    Loaded,
    Error
}

enum class LoadDataType {
    PullRefresh, MainRefresh, Append
}