package com.musicplayer.moviecatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.musicplayer.moviecatch.di.retrofit.RetrofitRepository
import com.musicplayer.moviecatch.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(private val repository: RetrofitRepository): ViewModel() {

    var popularMovieList: MutableLiveData<Movie> = MutableLiveData()

    fun getObserverLiveData(): MutableLiveData<Movie> {
        return popularMovieList
    }

    fun loadPopularData(page: String){
        repository.getPopularMovies(page, popularMovieList)
    }
}