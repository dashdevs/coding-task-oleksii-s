package com.example.sorokayassirtest.domain.usecase

import com.example.sorokayassirtest.domain.FilmsRepository
import com.example.sorokayassirtest.domain.entity.Film
import com.example.sorokayassirtest.domain.entity.LoadStatus
import javax.inject.Inject

class MainActivityUseCase @Inject constructor(private val filmsRepository: FilmsRepository) {

    fun savePageIndex(page: Int) = filmsRepository.savePageIndex(page)

    fun loadPageIndex(): Int = filmsRepository.loadPageIndex()

    suspend fun getFilmsList(page: Int): LoadStatus<List<Film>> = filmsRepository.getFilmsList(page)
}