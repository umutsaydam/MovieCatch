package com.musicplayer.moviecatch.di.dao.FavMovieDB


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavMovieDao {

    @Insert
    fun addMovie(movie: FavMovieData)

    @Query(value = "SELECT * FROM fav_movies")
    fun readAllMovies(): List<FavMovieData>

    @Query(value = "SELECT id FROM fav_movies WHERE id = :favID")
    fun isFav(favID: Int): Int

    @Query(value = "DELETE FROM fav_movies WHERE id = :favID")
    fun deleteFavMovie(favID: Int)
}