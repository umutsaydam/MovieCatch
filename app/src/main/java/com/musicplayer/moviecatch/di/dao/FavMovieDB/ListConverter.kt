package com.musicplayer.moviecatch.di.dao.FavMovieDB

import android.util.Log
import androidx.room.TypeConverter

class ListConverter {
    @TypeConverter
    fun fromString(value: String?): List<Int> {
        if (value == null || value == ""){
            return emptyList()
        }
        return value.split(",").map { it.toInt() }
    }

    @TypeConverter
    fun toString(value: List<Int>?): String {
        return value?.joinToString(",") ?: ""
    }
}
