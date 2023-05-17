package com.kudashov.learning_compose.screens.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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

    private val _state = mutableStateOf(PhotoDetailState())
    val state: State<PhotoDetailState> = _state


    fun loadPhotoDetail(id: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                photoDetail = photosRepository.getPhotoDetail(id)
            )
        }
    }
}