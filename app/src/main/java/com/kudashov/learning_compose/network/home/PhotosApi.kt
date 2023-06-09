package com.kudashov.learning_compose.network.home

import com.kudashov.learning_compose.network.BaseUrls
import com.kudashov.learning_compose.network.home.dto.PhotoDetailDto
import com.kudashov.learning_compose.network.home.dto.PhotoItemDto
import com.kudashov.learning_compose.network.home.dto.PhotoStatisticsDto
import com.kudashov.learning_compose.network.home.dto.TopicDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotosApi {

    @GET(BaseUrls.LIST_PHOTOS)
    suspend fun getImages(
        @Query("page") page: Int,
        @Query("per_page") photosPerPage: Int
    ): List<PhotoItemDto>

    @GET(BaseUrls.PHOTOS_BY_TOPIC)
    suspend fun getPhotosByTopic(
        @Path("id") id: String,
        @Query("page") page: Int,
        @Query("per_page") photosPerPage: Int
    ): List<PhotoItemDto>

    @GET(BaseUrls.TOPICS)
    suspend fun getTopics(): List<TopicDto>

    @GET(BaseUrls.RANDOM_PHOTO)
    suspend fun getRandomPhoto() : PhotoDetailDto

    @GET(BaseUrls.PHOTO_DETAIL)
    suspend fun getPhotoDetail(@Path("id") id: String): PhotoDetailDto

    @GET(BaseUrls.PHOTO_STATISTICS)
    suspend fun getPhotoStatistics(@Path("id") id: String): PhotoStatisticsDto
}