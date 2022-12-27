package com.example.sorokayassirtest.data

import com.example.sorokayassirtest.data.net.MovieApi
import com.example.sorokayassirtest.data.net.entity.MovieListResponse
import com.example.sorokayassirtest.domain.MoviesRepository
import com.example.sorokayassirtest.domain.entity.LoadStatus
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(private val movieApi: MovieApi) : MoviesRepository {

    override suspend fun getFilmsList(page: Int): LoadStatus<MovieListResponse> =
        try {
            LoadStatus.Success(movieApi.getMovieList(page))
        } catch (e: Exception) {
            LoadStatus.Error(e)
        }
}