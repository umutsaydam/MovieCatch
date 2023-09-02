package com.musicplayer.moviecatch.di.dao.FavMovieDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FavMovieData::class], version = 1, exportSchema = false)
@TypeConverters(ListConverter::class)
abstract class FavMovieDatabase : RoomDatabase() {
    abstract fun getFavMovieDAO(): FavMovieDao

    companion object {
        private var dbInstance: FavMovieDatabase? = null

        fun getFavMovieDB(context: Context): FavMovieDatabase {
            if (dbInstance == null) {
                dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    FavMovieDatabase::class.java,
                    "fav_movie"
                ).allowMainThreadQueries().build()
            }
            return dbInstance!!
        }
    }
}