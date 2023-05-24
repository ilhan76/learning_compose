package com.kudashov.learning_compose.base.paging

class Pager<Key, Item>(
    private val initialKey: Key,
    private inline val onLoadUpdated: (PagerLoadStatus) -> Unit,
    private inline val loadPage: suspend (nextKey: Key) -> Result<List<Item>>,
    private inline val getNextKey: suspend (List<Item>) -> Key,
    private inline val onError: suspend (Throwable?) -> Unit,
    private inline val onSuccess: suspend (List<Item>, Key, PagerLoadStatus) -> Unit
) {

    private var currentKey = initialKey
    private var loadStatus: PagerLoadStatus = PagerLoadStatus.Loaded

    suspend fun loadItems(loadDataType: LoadDataType) {
        if (loadStatus.isLoading()) return
        if (loadDataType != LoadDataType.Append) currentKey = initialKey
        loadStatus = when (loadDataType) {
            LoadDataType.PullRefresh -> PagerLoadStatus.PullRefreshLoading
            LoadDataType.MainRefresh -> PagerLoadStatus.MainLoading
            LoadDataType.Append -> PagerLoadStatus.AppendLoading
        }
        onLoadUpdated(loadStatus)

        val result = loadPage(currentKey)
        val items = result.getOrElse {
            onError(it)
            loadStatus = PagerLoadStatus.Error
            onLoadUpdated(loadStatus)
            return
        }
        currentKey = getNextKey(items)
        onSuccess(items, currentKey, loadStatus)
        loadStatus = PagerLoadStatus.Loaded
        onLoadUpdated(loadStatus)
    }
}
