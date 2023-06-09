package com.kudashov.learning_compose.network.home

import com.kudashov.learning_compose.base.domain.PhotoDetail
import com.kudashov.learning_compose.base.domain.PhotoItem
import com.kudashov.learning_compose.base.domain.PhotoStatistics
import com.kudashov.learning_compose.base.domain.Topic
import com.kudashov.learning_compose.base.domain.util.transform
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosRepository @Inject constructor(
    private val photosApi: PhotosApi
) {

    suspend fun getPhotos(page: Int, pageSize: Int): Result<List<PhotoItem>> {
        val startingIndex = page * pageSize
        return try {
            val photos = photosApi.getImages(startingIndex, pageSize).transform()
            Result.success(photos)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPhotosByTopics(
        id: String,
        page: Int,
        pageSize: Int
    ): Result<List<PhotoItem>> {
        val startingIndex = page * pageSize
        return try {
            val photos = photosApi.getPhotosByTopic(id, startingIndex, pageSize).transform()
            Result.success(photos)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTopicList(): List<Topic> = photosApi.getTopics().transform()

    suspend fun getRandomPhoto(): Result<PhotoDetail> {
        return try {
            val photos = photosApi.getRandomPhoto().transform()
            Result.success(photos)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPhotoDetail(id: String): PhotoDetail = photosApi.getPhotoDetail(id).transform()

    suspend fun getPhotoStatistics(id: String): Result<PhotoStatistics> {
     return try {
         val statistics = photosApi.getPhotoStatistics(id).transform()
         Result.success(statistics)
     } catch (e: Exception) {
         Result.failure(e)
     }
    }
}