package com.example.sorokayassirtest.domain

import com.example.sorokayassirtest.data.net.entity.MovieListResponse
import com.example.sorokayassirtest.domain.entity.LoadStatus

interface MoviesRepository {

    suspend fun getFilmsList(page: Int): LoadStatus<MovieListResponse>
}