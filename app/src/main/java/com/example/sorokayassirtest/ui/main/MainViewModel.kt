package com.example.sorokayassirtest.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.InvalidatingPagingSourceFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.sorokayassirtest.domain.MoviesRepository
import com.example.sorokayassirtest.ui.main.adapter.pagination.MoviesItemDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val moviesRepository: MoviesRepository) : ViewModel() {

    private val PAGE_SIZE = 20

    private val pagingSourceFactory = InvalidatingPagingSourceFactory {
        MoviesItemDataSource(moviesRepository)
    }

    val filmsFlow = Pager(
        PagingConfig(pageSize = PAGE_SIZE), pagingSourceFactory = pagingSourceFactory
    ).flow.cachedIn(viewModelScope)

    fun pressTryAgain() {
        pagingSourceFactory.invalidate()
    }
}