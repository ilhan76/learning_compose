package com.kudashov.learning_compose.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kudashov.learning_compose.network.home.HomeRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private var getListPhotosJob: Job? = null

    init {
        onEvent(HomeEvent.LoadPhotos)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.LoadPhotos -> onLoadPhotos()
        }
    }

    private fun onLoadPhotos() = viewModelScope.launch {
        getListPhotosJob?.cancel()
        getListPhotosJob = homeRepository.getListPhotos()
            .onEach {
                _state.value = _state.value.copy(
                    photos = it
                )
            }
            .launchIn(viewModelScope)
    }
}