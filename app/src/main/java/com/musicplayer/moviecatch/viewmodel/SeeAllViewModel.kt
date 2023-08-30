package com.musicplayer.moviecatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.musicplayer.moviecatch.di.retrofit.RetrofitRepository
import com.musicplayer.moviecatch.models.Movie
import com.musicplayer.moviecatch.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeeAllViewModel @Inject constructor(private val repository: RetrofitRepository) :
    ViewModel() {

    var moviesLiveData: MutableLiveData<Movie> = MutableLiveData()

    private var movieKey: String = ""

    fun getObserverLiveData(): MutableLiveData<Movie> {
        return moviesLiveData
    }

    fun loadMovies(page: String, query: String = "") {
        when (movieKey) {
            Constants.BUNDLE_SEE_ALL_POPULAR_KEY -> repository.getPopularMovies(page, moviesLiveData)
            Constants.BUNDLE_SEE_ALL_RECENT_KEY -> repository.getRecentMovies(page, moviesLiveData)
            Constants.BUNDLE_SEE_ALL_QUERY_KEY -> repository.getMoviesBySearched(page, query, moviesLiveData)
        }
    }

    fun setMovieKey(key: String){
        movieKey = key
    }

}