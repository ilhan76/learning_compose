package com.kudashov.learning_compose.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kudashov.learning_compose.network.home.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val photosRepository: PhotosRepository
): ViewModel() {

    var state by mutableStateOf(PhotoDetailState())
        private set

    fun loadPhotoDetail(id: String) {
        viewModelScope.launch {
            state = state.copy(
                photoDetail = photosRepository.getPhotoDetail(id)
            )
        }
    }

    fun loadPhotoStatistics(id: String) {
        viewModelScope.launch {
            val result = photosRepository.getPhotoStatistics(id)
            result.getOrNull()?.let {
                state = state.copy(photoStatistics = it)
            }
        }
    }
}