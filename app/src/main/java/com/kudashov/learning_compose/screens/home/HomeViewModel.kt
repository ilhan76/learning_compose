package com.kudashov.learning_compose.screens.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kudashov.learning_compose.network.home.PhotosRepository
import com.kudashov.learning_compose.screens.home.ui_data.TabItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val HOME_STATE = "HOME_STATE"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val photosRepository: PhotosRepository
) : ViewModel() {

    val state: StateFlow<HomeState> = savedStateHandle.getStateFlow(HOME_STATE, HomeState())

    val photos by lazy {
        photosRepository.getPagedListFlow()
            .cachedIn(viewModelScope)
    }

    fun loadTopics() = viewModelScope.launch {
        val topics = photosRepository.getTopicList()

        savedStateHandle[HOME_STATE] = state.value.copy(
            tabs = ItemCreator.createTopics(topics)
        )
    }

    fun onTabClicked(id: String) {
        val updatedTabs = state.value.tabs.map {
            if (it is TabItem.TextTabItem) {
                if (id == it.id) it.copy(isSelected = true)
                else it.copy(isSelected = false)
            } else {
                it
            }
        }

        savedStateHandle[HOME_STATE] = state.value.copy(
            tabs = updatedTabs
        )
    }
}