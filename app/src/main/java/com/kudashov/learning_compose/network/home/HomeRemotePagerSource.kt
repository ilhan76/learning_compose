package com.kudashov.learning_compose.network.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kudashov.learning_compose.domain.PhotoItem
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRemotePagerSource @Inject constructor(
    private val homeApi: HomeApi
) : PagingSource<Int, PhotoItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoItem> {
        val pageIndex = params.key ?: 1
        return try {
            val images = homeApi.getImages(pageIndex, params.loadSize)
            LoadResult.Page(
                data = images.map { it.transform() },
                prevKey = null,
                nextKey = if (images.size == params.loadSize) pageIndex + (params.loadSize / IMAGE_PAGE_SIZE) else null
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PhotoItem>): Int? = null
}