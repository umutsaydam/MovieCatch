package com.musicplayer.moviecatch.di.dao.FavMovieDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_movies")
data class FavMovieData(
    @PrimaryKey(autoGenerate = true)
    val movie_id :Int = 0,
    val backdrop_path: String? = "",
    val genre_ids: List<Int> = emptyList(),
    val id: Int = 0,
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String? = "",
    val release_date: String = "",
    val title: String = "",
    val vote_average: Double = 0.0,
    val genrestringTr: String = "",
    val genrestring: String = "",
)
