package com.musicplayer.moviecatch.di.dao.FavMovieDB

import androidx.room.TypeConverter

class ListConverter {
    @TypeConverter
    fun fromString(value: String?): List<Int> {
        return value?.split(",")?.map { it.toInt() } ?: emptyList()
    }

    @TypeConverter
    fun toString(value: List<Int>?): String {
        return value?.joinToString(",") ?: ""
    }
}
