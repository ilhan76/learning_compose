package com.kudashov.learning_compose.network.i_main

import com.kudashov.learning_compose.network.BaseUrls
import com.kudashov.learning_compose.network.i_main.dto.PhotoItemDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MainApi {

    @GET(BaseUrls.LIST_PHOTOS)
    suspend fun getImages(
        @Query("page") page: Int,
        @Query("per_page") photosPerPage: Int
    ): List<PhotoItemDto>
}