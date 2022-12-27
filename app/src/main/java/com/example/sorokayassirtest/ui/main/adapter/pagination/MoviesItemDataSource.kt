package com.example.sorokayassirtest.ui.main.adapter.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.sorokayassirtest.data.net.adapter.DomainModel
import com.example.sorokayassirtest.domain.FilmsRepository
import com.example.sorokayassirtest.domain.entity.LoadStatus
import com.example.sorokayassirtest.domain.entity.Movie
import javax.inject.Inject

class MoviesItemDataSource @Inject constructor(private val filmsRepository: FilmsRepository) : PagingSource<Int, Movie>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return when (val result = filmsRepository.getFilmsList(position)) {
            is LoadStatus.Error -> {
                LoadResult.Error(result.e)
            }
            is LoadStatus.Success -> {
                val pagedData = result.data
                val domainFilms: List<DomainModel> = with(com.example.sorokayassirtest.data.net.adapter.FilmModelAdapter) {
                    pagedData.films.convertToDomainModel()
                }
                LoadResult.Page(
                    data = domainFilms,
                    prevKey = if (position > 1) position - 1 else null,
                    nextKey = if (pagedData.totalPages > pagedData.page) pagedData.page + 1 else null
                )
            }
            else -> {
                LoadResult.Invalid()
            }
        }
    }
}