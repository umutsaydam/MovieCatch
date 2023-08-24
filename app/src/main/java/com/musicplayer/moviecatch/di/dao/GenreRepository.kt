package com.musicplayer.moviecatch.di.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class GenreRepository @Inject constructor(private val genreDao: GenreDao) {

    val readAllData: List<GenreData> = genreDao.readAllData()

    fun addGenre(genre: GenreData) {
        genreDao.addGenre(genre)
    }

    fun addAllGenre(genreList: List<GenreData>) {
        genreDao.addAllGenres(genreList)
    }
}