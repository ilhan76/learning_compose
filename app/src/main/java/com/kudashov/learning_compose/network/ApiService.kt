package com.kudashov.learning_compose.network

import com.kudashov.learning_compose.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BaseUrls.BASE_URL)
                .setClient()
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    private fun Retrofit.Builder.setClient() = apply {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addHttpLoggingInterceptor()
            .addQueryInterceptor()
            .build()

        this.client(okHttpClient)
    }

    private fun OkHttpClient.Builder.addHttpLoggingInterceptor() = apply {
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)

            this.addNetworkInterceptor(loggingInterceptor)
        }
    }

    private fun OkHttpClient.Builder.addQueryInterceptor() = apply {
        val interceptor = Interceptor { chain ->
            var request = chain.request()
            val url = request.url.newBuilder()
                .addQueryParameter("client_id", "AxKndLskhLmXg2fonbOa79Hd4Cm9E1Zm73Xvl9_6Bvw")
                .build()
            request = request.newBuilder()
                .url(url)
                .build()
            chain.proceed(request)
        }
        this.addInterceptor(interceptor)
    }
}