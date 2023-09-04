package com.musicplayer.moviecatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.musicplayer.moviecatch.di.dao.FavMovieDB.FavMovieData
import com.musicplayer.moviecatch.di.dao.FavMovieDB.FavMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavMovieViewModel @Inject constructor(private val favMovieRepository: FavMovieRepository) :
    ViewModel() {

    private var allData: MutableLiveData<List<FavMovieData>> = MutableLiveData()

    fun getObserverFavMovies(): MutableLiveData<List<FavMovieData>> {
        return allData
    }

    fun addFavMovie(movie: FavMovieData) {
        favMovieRepository.addMovie(movie)
    }

    fun isFavMovie(movieID: Int): Boolean {
        if (favMovieRepository.isFav(movieID) == 0){
            return false
        }
        return true
    }

    fun refreshData(){
        favMovieRepository.refreshData()
        loadAllData()
    }

    fun loadAllData() {
        allData.postValue(favMovieRepository.readAllData)
    }

   fun deleteFavMovie(favID: Int){
        favMovieRepository.deleteFunMovie(favID)
    }
}