package com.musicplayer.moviecatch.models

import com.google.gson.annotations.SerializedName

data class Result(
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    var release_date: String,
    val title: String,
    val vote_average: Double,
    var genrestringTr: String,
    var genrestring: String
)
