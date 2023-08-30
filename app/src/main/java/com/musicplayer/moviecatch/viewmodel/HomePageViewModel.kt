package com.musicplayer.moviecatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.musicplayer.moviecatch.di.retrofit.RetrofitRepository
import com.musicplayer.moviecatch.models.Genre
import com.musicplayer.moviecatch.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(private val repository: RetrofitRepository) :
    ViewModel() {

    var popularMovieList: MutableLiveData<Movie> = MutableLiveData()
    var recentMovieList: MutableLiveData<Movie> = MutableLiveData()
    var genreList: MutableLiveData<Genre> = MutableLiveData()

    fun getObserverLiveData(isPopular: Boolean): MutableLiveData<Movie> {
        if (isPopular) {
            return popularMovieList
        }
        return recentMovieList
    }

    fun getObserverGenre(): MutableLiveData<Genre> {
        return genreList
    }

    fun loadRecentData(page: String) {
        repository.getRecentMovies(page, recentMovieList)
    }

    fun loadPopularData(page: String) {
        repository.getPopularMovies(page, popularMovieList)
    }


    fun loadGenreData() {
        repository.getAllGenres(genreList)
    }
}