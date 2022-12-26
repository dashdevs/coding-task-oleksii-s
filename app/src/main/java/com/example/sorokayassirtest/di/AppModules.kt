package com.example.sorokayassirtest.di

import android.content.Context
import com.example.sorokayassirtest.BuildConfig
import com.example.sorokayassirtest.data.FilmsRepositoryImpl
import com.example.sorokayassirtest.data.net.FilmApi
import com.example.sorokayassirtest.data.net.interceptor.ApiAuthInterceptor
import com.example.sorokayassirtest.data.net.moshi
import com.example.sorokayassirtest.data.prefs.PreferencesAPI
import com.example.sorokayassirtest.domain.FilmsRepository
import com.example.sorokayassirtest.domain.usecase.MainActivityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
     * Creates an instance of [PreferencesAPI]
     */
    @Singleton
    @Provides
    fun providePreferencesAPI(
        @ApplicationContext context: Context
    ) = PreferencesAPI(context)

    /**
     * Creates an instance of [FilmsRepositoryImpl]
     */
    @Singleton
    @Provides
    fun provideRepository(
        prefs: PreferencesAPI,
        api: FilmApi
    ) = FilmsRepositoryImpl(prefs, api) as FilmsRepository

    /**
     * Creates an instance of [MainActivityUseCase]
     */
    @Singleton
    @Provides
    fun provideMainActivityUseCase(
        filmsRepository: FilmsRepository
    ) = MainActivityUseCase(filmsRepository)

    /**
     * DI function that provides the retrofit client object.
     */
    @Singleton
    @Provides
    fun provideFilmApi(): FilmApi {
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
            .create(FilmApi::class.java)
    }
}