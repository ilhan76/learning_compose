package com.kudashov.learning_compose.screens.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kudashov.learning_compose.network.home.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

const val HOME_STATE = "HOME_STATE"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val photosRepository: PhotosRepository
) : ViewModel() {

    val state: StateFlow<HomeState> = savedStateHandle.getStateFlow(HOME_STATE, HomeState())

    init {
        savedStateHandle[HOME_STATE] = state.value.copy(
            tabs = ItemCreator.getTabItems()
        )
    }

    val photos by lazy {
        photosRepository.getPagedListFlow()
            .cachedIn(viewModelScope)
    }

    fun onTabClicked(id: String) {
        savedStateHandle[HOME_STATE] = state.value.copy(
            tabs = ItemCreator.getTabItems().map {
                if (id == it.id) it.copy(isSelected = true)
                else it.copy(isSelected = false)
            }
        )
    }
}