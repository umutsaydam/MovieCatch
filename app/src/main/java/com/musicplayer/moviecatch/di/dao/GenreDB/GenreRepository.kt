package com.musicplayer.moviecatch.di.dao.GenreDB

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