package com.musicplayer.moviecatch.di.module

import android.app.Application
import com.musicplayer.moviecatch.di.dao.GenreDao
import com.musicplayer.moviecatch.di.dao.GenreDatabase
import com.musicplayer.moviecatch.di.retrofit.RetrofitServiceInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private val baseURL = "https://api.themoviedb.org/"

    @Provides
    @Singleton
    fun getAppDB(context: Application): GenreDatabase {
        return GenreDatabase.getAppDB(context)
    }

    @Provides
    @Singleton
    fun getDao(appDB: GenreDatabase): GenreDao {
        return appDB.getDAO()
    }

    @Provides
    @Singleton
    fun getRetrofitServiceInstance(retrofit: Retrofit): RetrofitServiceInstance {
        return retrofit.create(RetrofitServiceInstance::class.java)
    }

    @Provides
    @Singleton
    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}