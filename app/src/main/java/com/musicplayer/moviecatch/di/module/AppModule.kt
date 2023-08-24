package com.musicplayer.moviecatch.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.musicplayer.moviecatch.di.dao.GenreDao
import com.musicplayer.moviecatch.di.dao.GenreDatabase
import com.musicplayer.moviecatch.di.retrofit.RetrofitServiceInstance
import com.musicplayer.moviecatch.prefs.SessionManager
import com.musicplayer.moviecatch.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideSharedPreferences(@ApplicationContext context: Context) =
        context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideSessionManager(preferences: SharedPreferences) = SessionManager(preferences)

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