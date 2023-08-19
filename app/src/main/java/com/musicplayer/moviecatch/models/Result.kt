package com.musicplayer.moviecatch.models

data class Result(
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    var release_date: String,
    var genrestring: String
)
