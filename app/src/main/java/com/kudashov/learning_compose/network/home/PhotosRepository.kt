package com.kudashov.learning_compose.network.home

import com.kudashov.learning_compose.base.domain.PhotoDetail
import com.kudashov.learning_compose.base.domain.PhotoItem
import com.kudashov.learning_compose.base.domain.Topic
import com.kudashov.learning_compose.base.domain.util.transform
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

const val IMAGE_PAGE_SIZE = 20

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

    suspend fun getTopicList(): List<Topic> = photosApi.getTopics().transform()

    suspend fun getRandomPhoto(): PhotoDetail = photosApi.getRandomPhoto().transform()

    suspend fun getPhotoDetail(id: String): PhotoDetail = photosApi.getPhotoDetail(id).transform()
}