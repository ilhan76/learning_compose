package com.kudashov.learning_compose.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kudashov.learning_compose.network.home.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val photosRepository: PhotosRepository
) : ViewModel() {

    val photos by lazy {
        photosRepository.getPagedListFlow()
            .cachedIn(viewModelScope)
    }
}