package com.example.sorokayassirtest.di

import com.example.sorokayassirtest.BuildConfig
import com.example.sorokayassirtest.data.MoviesRepositoryImpl
import com.example.sorokayassirtest.data.net.MovieApi
import com.example.sorokayassirtest.data.net.interceptor.ApiAuthInterceptor
import com.example.sorokayassirtest.data.net.moshi
import com.example.sorokayassirtest.domain.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Dagger hilt object class that handles object creation.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModules {
    /**
     * Creates an instance of [MoviesRepositoryImpl]
     */
    @Singleton
    @Provides
    fun provideRepository(api: MovieApi) = MoviesRepositoryImpl(api) as MoviesRepository

    /**
     * DI function that provides the retrofit client object.
     */
    @Singleton
    @Provides
    fun provideFilmApi(): MovieApi {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.addInterceptor(ApiAuthInterceptor())

        if (BuildConfig.DEBUG)
            okHttpClientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        val okHttpClient = okHttpClientBuilder.build()

        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
            .create(MovieApi::class.java)
    }
}