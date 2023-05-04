package com.kudashov.learning_compose.network.home

import com.kudashov.learning_compose.domain.PhotoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeRepository(private val api: HomeApi) {

    fun getListPhotos(
        pageNumber: Int = 1,
        photosPerPage: Int = 10
    ): Flow<List<PhotoItem>> = flow {
        emit(api.getImages(pageNumber, photosPerPage).map { it.transform() })
    }
}