package com.musicplayer.moviecatch.di.dao.GenreDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GenreDao {

    @Insert
    fun addGenre(genre: GenreData)

    @Insert
    fun addAllGenres(objects: List<GenreData>)

    @Query("SELECT * FROM genres")
    fun readAllData(): List<GenreData>
}