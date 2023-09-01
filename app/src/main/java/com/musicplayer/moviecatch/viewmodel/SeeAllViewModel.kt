package com.musicplayer.moviecatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.musicplayer.moviecatch.Paging.MoviePagingSource
import com.musicplayer.moviecatch.di.retrofit.RetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeeAllViewModel @Inject constructor(private val repository: RetrofitRepository) :
    ViewModel() {

    var loading: MutableLiveData<Boolean> = MutableLiveData()
    var movieList = Pager(PagingConfig(pageSize = 20, maxSize = 100)) {
        MoviePagingSource(repository)
    }.flow.cachedIn(viewModelScope)
}