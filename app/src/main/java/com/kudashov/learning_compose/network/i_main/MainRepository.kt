package com.kudashov.learning_compose.network.i_main

import com.kudashov.learning_compose.domain.PhotoItem

class MainRepository(private val api: MainApi) {

    suspend fun getListPhotos(
        pageNumber: Int = 1,
        photosPerPage: Int = 10
    ): List<PhotoItem> =
        api.getImages(pageNumber, photosPerPage)
        .map { it.transform() }
}