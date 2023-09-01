package com.musicplayer.moviecatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.musicplayer.moviecatch.Paging.MoviePagingSource
import com.musicplayer.moviecatch.di.retrofit.RetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeeAllViewModel @Inject constructor(private val repository: RetrofitRepository) :
    ViewModel() {

    var loading: MutableLiveData<Boolean> = MutableLiveData()
    private var seeAllMovieKey: String? = null
    private var searchQuery: String = ""

    var movieList = Pager(PagingConfig(pageSize = 20, maxSize = 100)) {
        MoviePagingSource(repository, seeAllMovieKey!!, searchQuery)
    }.flow.cachedIn(viewModelScope)

    fun setMovieKey(key: String) {
        seeAllMovieKey = key
    }

    fun setSearchQuery(query: String) {
        searchQuery = query
    }
}