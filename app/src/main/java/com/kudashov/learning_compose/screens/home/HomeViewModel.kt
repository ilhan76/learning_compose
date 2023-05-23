package com.kudashov.learning_compose.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kudashov.learning_compose.domain.util.LoadStatus
import com.kudashov.learning_compose.domain.util.LoadableData
import com.kudashov.learning_compose.network.home.PhotosRepository
import com.kudashov.learning_compose.screens.home.ItemCreator.EDITORIAL_ID
import com.kudashov.learning_compose.screens.home.ItemCreator.RANDOM_PHOTO_ID
import com.kudashov.learning_compose.screens.home.ui_data.TabItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val photosRepository: PhotosRepository
) : ViewModel() {

    var state by mutableStateOf(HomeState())

    val photos by lazy {
        photosRepository.getPagedListFlow()
            .cachedIn(viewModelScope)
    }

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

    private fun reactOnTabSelected(selectedTabId: String) = when (selectedTabId) {
        EDITORIAL_ID -> {}
        RANDOM_PHOTO_ID -> loadRandomPhoto()
        else -> {
            // todo
        }
    }

    private fun loadRandomPhoto() {
        viewModelScope.launch {
            state = state.copy(randomPhoto = LoadableData(null, LoadStatus.Loading))

            state = state.copy(
                randomPhoto = LoadableData(photosRepository.getRandomPhoto(), LoadStatus.Loaded)
            )
        }
    }
}