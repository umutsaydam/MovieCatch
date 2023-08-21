package com.musicplayer.moviecatch.di.retrofit

import com.musicplayer.moviecatch.models.Genre
import com.musicplayer.moviecatch.models.Movie
import com.musicplayer.moviecatch.models.Trailer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitServiceInstance {
    @GET("3/movie/popular?api_key=99effc409b6cda23954144eafe0cb25d")
    fun getPopularVideos(@Query("page") query: String): Call<Movie>

    @GET("3/movie/now_playing?api_key=99effc409b6cda23954144eafe0cb25d")
    fun getRecentVideos(@Query("page") query: String): Call<Movie>

    @GET("3/movie/list?api_key=99effc409b6cda23954144eafe0cb25d")
    fun getGenres(): Call<Genre>

    @GET("3/movie/{id}/videos?api_key=99effc409b6cda23954144eafe0cb25d")
    fun getTrailerTeasers(@Path("id") query: String): Call<Trailer>

    @GET("3/movie/movie?api_key=99effc409b6cda23954144eafe0cb25d")
    fun getSuggestion(@Query("query") query: String): Call<Movie>
}