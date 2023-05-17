package com.kudashov.learning_compose.network.home

import com.kudashov.learning_compose.network.BaseUrls
import com.kudashov.learning_compose.network.home.dto.PhotoDetailDto
import com.kudashov.learning_compose.network.home.dto.PhotoItemDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotosApi {

    @GET(BaseUrls.LIST_PHOTOS)
    suspend fun getImages(
        @Query("page") page: Int,
        @Query("per_page") photosPerPage: Int
    ): List<PhotoItemDto>

    @GET("${BaseUrls.LIST_PHOTOS}/{id}")
    suspend fun getPhotoDetail(@Path("id") id: String): PhotoDetailDto
}