package com.musicplayer.moviecatch.di.retrofit

import com.musicplayer.moviecatch.models.Genre
import com.musicplayer.moviecatch.models.Movie
import com.musicplayer.moviecatch.models.Trailer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitServiceInstance {
    @GET("3/movie/popular?apikey=802b2c4b88ea1183e50e6b285a27696e")
    fun getPopularVideos(@Query("page") query: String): Call<Movie>

    @GET("3/movie/now_playing?apikey=802b2c4b88ea1183e50e6b285a27696e")
    fun getRecentVideos(@Query("page") query: String): Call<Movie>

    @GET("3/movie/list?apikey=802b2c4b88ea1183e50e6b285a27696e")
    fun getGenres(): Call<Genre>

    @GET("3/movie/{id}/videos?apikey=802b2c4b88ea1183e50e6b285a27696e")
    fun getTrailerTeasers(@Path("id") query: String): Call<Trailer>

    @GET("3/movie/movie?apikey=802b2c4b88ea1183e50e6b285a27696e")
    fun getSuggestion(@Query("query") query: String): Call<Movie>
}