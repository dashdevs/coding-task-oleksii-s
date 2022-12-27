package com.example.sorokayassirtest.domain

import com.example.sorokayassirtest.data.net.entity.MovieListResponse
import com.example.sorokayassirtest.domain.entity.Movie
import com.example.sorokayassirtest.domain.entity.LoadStatus

interface FilmsRepository {

    suspend fun getFilmsList(page: Int): LoadStatus<MovieListResponse>
}