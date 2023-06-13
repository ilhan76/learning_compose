package com.kudashov.learning_compose.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kudashov.learning_compose.base.domain.PhotoItem
import com.kudashov.learning_compose.base.domain.util.LoadStatus
import com.kudashov.learning_compose.base.domain.util.LoadableData
import com.kudashov.learning_compose.base.paging.Pager
import com.kudashov.learning_compose.base.paging.LoadDataType
import com.kudashov.learning_compose.base.paging.PagerLoadStatus
import com.kudashov.learning_compose.network.home.PhotosRepository
import com.kudashov.learning_compose.screens.home.ItemCreator.EDITORIAL_ID
import com.kudashov.learning_compose.screens.home.ItemCreator.RANDOM_PHOTO_ID
import com.kudashov.learning_compose.screens.home.ui_data.TabItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val IMAGE_PAGE_SIZE = 20

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val photosRepository: PhotosRepository
) : ViewModel() {

    var state by mutableStateOf(HomeState())

    private val editorialPager = Pager(
        initialKey = state.page,
        onLoadUpdated = ::pagerOnLoadUpdated,
        loadPage = { nextPage ->
            photosRepository.getPhotos(nextPage, IMAGE_PAGE_SIZE)
        },
        getNextKey = ::pagerGetNextKey,
        onError = ::pagerOnError,
        onSuccess = ::pagerOnSuccess
    )

    private val topicPager = Pager(
        initialKey = state.page,
        onLoadUpdated = ::pagerOnLoadUpdated,
        loadPage = { nextPage ->
            photosRepository.getPhotosByTopics(state.selectedTopicId, nextPage, IMAGE_PAGE_SIZE)
        },
        getNextKey = ::pagerGetNextKey,
        onError = ::pagerOnError,
        onSuccess = ::pagerOnSuccess
    )

    fun loadTopics() = viewModelScope.launch {
        val topics = photosRepository.getTopicList()

        state = state.copy(
            tabs = ItemCreator.createTopics(topics)
        )
    }

    fun onTabClicked(id: String) {
        val updatedTabs: List<TabItem> = state.tabs.map {
            if (it is TabItem.TextTabItem) {
                if (id == it.id) it.copy(isSelected = true)
                else it.copy(isSelected = false)
            } else {
                it
            }
        }

        state = state.copy(tabs = updatedTabs)
        reactOnTabSelected(state.selectedTopicId)
    }

    fun loadPhotos(loadDataType: LoadDataType = LoadDataType.MainRefresh) {
        viewModelScope.launch {
            if (state.selectedTopicId == EDITORIAL_ID) editorialPager.loadItems(loadDataType)
            else topicPager.loadItems(loadDataType)
        }
    }

    private fun reactOnTabSelected(selectedTabId: String) = when (selectedTabId) {
        RANDOM_PHOTO_ID -> loadRandomPhoto()
        else -> loadPhotos(LoadDataType.MainRefresh)
    }

    private fun loadRandomPhoto() {
        viewModelScope.launch {
            state = state.copy(randomPhoto = LoadableData(null, LoadStatus.Loading))

            val result = photosRepository.getRandomPhoto()
            if (result.isSuccess) {
                state = state.copy(
                    randomPhoto = LoadableData(result.getOrNull(), LoadStatus.Loaded)
                )
            } else {
                val e = result.exceptionOrNull()
                // todo Обработать
            }
        }
    }

    //region Pager
    private fun pagerOnLoadUpdated(loadStatus: PagerLoadStatus) {
        state = state.copy(loadStatus = loadStatus)
    }

    private fun pagerGetNextKey(photos: List<PhotoItem>) = state.page + 1

    private fun pagerOnError(error: Throwable?) {
        // todo
    }

    private fun pagerOnSuccess(
        items: List<PhotoItem>,
        newKey: Int,
        loadStatus: PagerLoadStatus
    ) {
        val photos = when(loadStatus) {
            PagerLoadStatus.AppendLoading -> state.photos + items
            else -> items
        }
        state = state.copy(
            photos = photos,
            page = newKey
        )
    }
    //endregion
}