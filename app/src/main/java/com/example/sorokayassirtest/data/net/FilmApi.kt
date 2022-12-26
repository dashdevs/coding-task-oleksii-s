package com.example.sorokayassirtest.data.net

import com.example.sorokayassirtest.data.net.entity.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API communication setup via Retrofit.
 */
interface FilmApi {

    @GET("/3/discover/movie")
    suspend fun getMovieList(@Query("page") pageIndex: Int?): Response
}