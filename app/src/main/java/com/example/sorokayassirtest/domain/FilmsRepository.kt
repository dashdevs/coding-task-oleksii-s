package com.example.sorokayassirtest.domain

import com.example.sorokayassirtest.domain.entity.Film
import com.example.sorokayassirtest.domain.entity.LoadStatus

interface FilmsRepository {

    fun savePageIndex(page: Int)

    fun loadPageIndex(): Int

    suspend fun getFilmsList(page: Int): LoadStatus<List<Film>>
}