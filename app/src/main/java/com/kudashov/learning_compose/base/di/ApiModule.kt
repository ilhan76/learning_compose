package com.kudashov.learning_compose.base.di

import com.kudashov.learning_compose.network.home.PhotosApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideHomeApi(retrofit: Retrofit): PhotosApi = retrofit.create(PhotosApi::class.java)
}