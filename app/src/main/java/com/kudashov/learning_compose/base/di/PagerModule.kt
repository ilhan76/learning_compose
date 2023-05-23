package com.kudashov.learning_compose.base.di

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.kudashov.learning_compose.base.domain.PhotoItem
import com.kudashov.learning_compose.network.home.PhotosApi
import com.kudashov.learning_compose.network.home.PhotosRemotePagerSource
import com.kudashov.learning_compose.network.home.IMAGE_PAGE_SIZE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PagerModule {

    @Provides
    @Singleton
    fun provideHomePager(photosApi: PhotosApi): Pager<Int, PhotoItem> {
        return Pager(
            config = PagingConfig(
                pageSize = IMAGE_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PhotosRemotePagerSource(photosApi) }
        )
    }
}