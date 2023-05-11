package com.kudashov.learning_compose.di

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.kudashov.learning_compose.domain.PhotoItem
import com.kudashov.learning_compose.network.home.HomeRemotePagerSource
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
    fun provideHomePager(
        homeRemotePagerSource: HomeRemotePagerSource
    ): Pager<Int, PhotoItem> {
        return Pager(
            config = PagingConfig(
                pageSize = IMAGE_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { homeRemotePagerSource }
        )
    }
}