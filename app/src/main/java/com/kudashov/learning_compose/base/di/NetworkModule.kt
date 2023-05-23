package com.kudashov.learning_compose.base.di

import com.kudashov.learning_compose.BuildConfig
import com.kudashov.learning_compose.network.BaseUrls
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BaseUrls.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @Named(CLIENT_ID_INTERCEPTOR) clientIdInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(clientIdInterceptor)
        .build()

    @Provides
    @Singleton
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = when (BuildConfig.DEBUG) {
                true -> HttpLoggingInterceptor.Level.BODY
                false -> HttpLoggingInterceptor.Level.BASIC
            }
        }
    }

    @Provides
    @Singleton
    @Named(CLIENT_ID_INTERCEPTOR)
    internal fun provideClientIdInterceptor(): Interceptor = Interceptor { chain ->
        var request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("client_id", "AxKndLskhLmXg2fonbOa79Hd4Cm9E1Zm73Xvl9_6Bvw")
            .build()
        request = request.newBuilder()
            .url(url)
            .build()
        chain.proceed(request)
    }

    companion object {
        const val CLIENT_ID_INTERCEPTOR = "CLIENT_ID_INTERCEPTOR"
    }
}