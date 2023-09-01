package com.musicplayer.moviecatch.di.retrofit

import com.musicplayer.moviecatch.models.Genre
import com.musicplayer.moviecatch.models.Movie
import com.musicplayer.moviecatch.models.Trailer
import com.musicplayer.moviecatch.models.YoutubeTrailerModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface RetrofitServiceInstance {
    @GET("3/movie/popular?api_key=99effc409b6cda23954144eafe0cb25d")
    fun getPopularVideos(@Query("page") query: String): Call<Movie>

    @GET("3/movie/popular?api_key=99effc409b6cda23954144eafe0cb25d")
    suspend fun getPopularVideos2(@Query("page") query: String): Response<Movie>

    @GET("3/movie/now_playing?api_key=99effc409b6cda23954144eafe0cb25d")
    fun getRecentVideos(@Query("page") query: String): Call<Movie>

    @GET("3/genre/movie/list?api_key=99effc409b6cda23954144eafe0cb25d")
    fun getGenres(): Call<Genre>

    @GET("3/movie/{id}/videos?api_key=99effc409b6cda23954144eafe0cb25d")
    fun getTrailer(@Path("id") id: String): Call<Trailer>

    @GET
    fun getVideos(@Url url: String): Call<YoutubeTrailerModel>

    @GET("3/search/movie?api_key=99effc409b6cda23954144eafe0cb25d")
    fun getMoviesBySearched(@Query("page") page: String, @Query("query") query: String): Call<Movie>
}