package com.kudashov.learning_compose.network.home

import androidx.paging.Pager
import androidx.paging.PagingData
import com.kudashov.learning_compose.domain.PhotoItem
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

    suspend fun getPhotoDetail(id: String) = photosApi.getPhotoDetail(id).transform()
}