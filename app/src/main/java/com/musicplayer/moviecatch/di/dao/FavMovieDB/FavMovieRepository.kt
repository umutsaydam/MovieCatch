package com.musicplayer.moviecatch.di.dao.FavMovieDB


import javax.inject.Inject

class FavMovieRepository @Inject constructor(private val favMovieDao: FavMovieDao) {

    var readAllData : List<FavMovieData> = favMovieDao.readAllMovies()

    fun addMovie(movie: FavMovieData){
        favMovieDao.addMovie(movie)
    }

    fun isFav(movieID: Int): Int{
        return favMovieDao.isFav(movieID)
    }

    fun deleteFunMovie(favID: Int){
        favMovieDao.deleteFavMovie(favID)
    }
    fun refreshData(){
        readAllData = favMovieDao.readAllMovies()
    }
}