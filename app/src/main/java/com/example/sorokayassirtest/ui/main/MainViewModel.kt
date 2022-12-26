package com.example.sorokayassirtest.ui.main

import androidx.lifecycle.*
import com.example.sorokayassirtest.domain.entity.Film
import com.example.sorokayassirtest.domain.entity.Item
import com.example.sorokayassirtest.domain.entity.LoadStatus
import com.example.sorokayassirtest.domain.entity.Networking
import com.example.sorokayassirtest.domain.usecase.MainActivityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val useCase: MainActivityUseCase) : ViewModel() {

    private var _filmsLiveData = MutableLiveData<List<Item>>()
    val filmsLiveData: LiveData<List<Item>> = _filmsLiveData

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState
    var pageIndex = 1

    private val _newFilmsPageReloadTrigger = MutableLiveData(0L)
    private val _filmsUpdate: LiveData<LoadStatus<List<Film>>> =
        Transformations.switchMap(_newFilmsPageReloadTrigger) {
            liveData(Dispatchers.IO) {
                emit(LoadStatus.Loading)
                emit(useCase.getFilmsList(pageIndex))
            }
        }

    init {
        pageIndex = useCase.loadPageIndex()
        _filmsUpdate.observeForever { loadStatus ->
            when (loadStatus) {
                is LoadStatus.Loading -> {
                    val loading = Networking("Loading")
                    _viewState.value = ViewState(isNetworking = true)
                    val currentList = _filmsLiveData.value?.toMutableList()
                    if (currentList.isNullOrEmpty()) {
                        _filmsLiveData.value = listOf(loading)
                    } else {
                        currentList.add(0, loading)
                        currentList.add(currentList.size, loading)
                        _filmsLiveData.value = currentList!!
                    }
                }
                is LoadStatus.Error -> {
                    val currentList = _filmsLiveData.value?.filterIsInstance<Film>()?.toMutableList<Item>()
                    val error = Networking(isItError = true, message = loadStatus.e.message ?: "Error")
                    if (currentList.isNullOrEmpty()) {
                        _filmsLiveData.value = listOf(error)
                    } else {
                        currentList.add(0, error)
                        currentList.add(currentList.size, error)
                        _filmsLiveData.value = currentList!!
                    }
                    _viewState.value =
                        ViewState(errorMessage = loadStatus.e.message)
                }
                is LoadStatus.Success -> {
                    _viewState.value = ViewState(isNetworking = false)
                    _filmsLiveData.value = _filmsLiveData.value?.filterIsInstance<Film>()?.plus(loadStatus.data)
                    useCase.savePageIndex(pageIndex)
                }
            }
        }
        loadFilmsPageAcceptsCache()
    }

    data class ViewState(
        val isNetworking: Boolean = false,
        val errorMessage: String? = null
    )

    private fun loadFilmsPageAcceptsCache() {
        _newFilmsPageReloadTrigger.value = -1L
    }

    fun loadFilmsPage() {
        _newFilmsPageReloadTrigger.value = System.currentTimeMillis()
    }

    fun refreshList() {
        pageIndex = 1
        _filmsLiveData.value = emptyList()
        loadFilmsPage()
    }

    fun pressTryAgain() {
        _viewState.value = ViewState(isNetworking = false)
        pageIndex++
        loadFilmsPage()
    }
}