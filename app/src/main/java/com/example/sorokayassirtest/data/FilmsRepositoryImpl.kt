package com.example.sorokayassirtest.data

import android.util.Log
import com.example.sorokayassirtest.data.net.FilmApi
import com.example.sorokayassirtest.data.prefs.PreferencesAPI
import com.example.sorokayassirtest.domain.FilmsRepository
import com.example.sorokayassirtest.domain.entity.Film
import com.example.sorokayassirtest.domain.entity.LoadStatus
import javax.inject.Inject

class FilmsRepositoryImpl @Inject constructor(
    private val prefs: PreferencesAPI,
    private val filmApi: FilmApi
) : FilmsRepository {
    override fun savePageIndex(page: Int) {
        prefs.savePageIndex(page)
    }

    override fun loadPageIndex(): Int {
        return prefs.loadPageIndex()
    }

    override suspend fun getFilmsList(page: Int): LoadStatus<List<Film>> =
        try {
            //Get films from API
            val r = filmApi.getMovieList(page)
            Log.d("kek","r $r")
            val response = r.films
            val domainFilms = with(com.example.sorokayassirtest.data.net.adapter.FilmModelAdapter) {
                response.convertToDBModel()
            }
            LoadStatus.Success(domainFilms)
        } catch (e: Exception) {
            if (page > 1) LoadStatus.Error(e)
            else LoadStatus.Error(e)
        }
}