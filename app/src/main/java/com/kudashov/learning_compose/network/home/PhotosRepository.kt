package com.kudashov.learning_compose.network.home

import androidx.paging.Pager
import androidx.paging.PagingData
import com.kudashov.learning_compose.domain.PhotoDetail
import com.kudashov.learning_compose.domain.PhotoItem
import com.kudashov.learning_compose.domain.Topic
import com.kudashov.learning_compose.domain.util.transform
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

const val IMAGE_PAGE_SIZE = 20

@Singleton
class PhotosRepository @Inject constructor(
    private val pager: Pager<Int, PhotoItem>,
    private val photosApi: PhotosApi
) {

    fun getPagedListFlow(): Flow<PagingData<PhotoItem>> = pager.flow

    suspend fun getTopicList(): List<Topic> = photosApi.getTopics().transform()

    suspend fun getRandomPhoto(): PhotoDetail = photosApi.getRandomPhoto().transform()

    suspend fun getPhotoDetail(id: String): PhotoDetail = photosApi.getPhotoDetail(id).transform()
}