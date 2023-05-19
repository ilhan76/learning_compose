package com.kudashov.learning_compose.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kudashov.learning_compose.network.home.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    init {
        onEvent(HomeEvent.LoadPhotos)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            else -> {
                // do nothing
            }
        }
    }

    fun getPhotos() = homeRepository.getPagedListFlow()
        .cachedIn(viewModelScope)
}