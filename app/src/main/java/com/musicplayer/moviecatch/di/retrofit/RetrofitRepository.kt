package com.musicplayer.moviecatch.di.retrofit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.musicplayer.moviecatch.models.Genre
import com.musicplayer.moviecatch.models.Movie
import com.musicplayer.moviecatch.models.ResultX
import com.musicplayer.moviecatch.models.Trailer
import com.musicplayer.moviecatch.models.YoutubeTrailerModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RetrofitRepository @Inject constructor(private val retrofitServiceInstance: RetrofitServiceInstance) {

    fun getPopularMovies(page: String, liveData: MutableLiveData<Movie>) {
        retrofitServiceInstance.getPopularVideos(page).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                liveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                liveData.postValue(null)
            }

        })
    }

    suspend fun getPopularMovies2(page: String): MutableList<Movie> {
        Log.d("R8/W", page+" page degeri")

        val liveData: MutableList<Movie> = mutableListOf()

        val a = retrofitServiceInstance.getPopularVideos2(page)
        if (!a.isSuccessful){
            Log.d("R8/W", a.message()+" retrofit hataaa")
        }else{
            Log.d("R8/W", a.message()+" retrofit basarili")
            liveData.add(a.body()!!)
            //Log.d("R8/W", a.body().toString())
        }
        return liveData
    }

    fun getAllGenres(liveData: MutableLiveData<Genre>) {
        retrofitServiceInstance.getGenres().enqueue(object : Callback<Genre> {
            override fun onResponse(call: Call<Genre>, response: Response<Genre>) {
                liveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Genre>, t: Throwable) {
                Log.d("R/T", t.message.toString())
                liveData.postValue(null)
            }

        })
    }

    fun getRecentMovies(page: String, liveData: MutableLiveData<Movie>) {
        retrofitServiceInstance.getRecentVideos(page).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                Log.d("R/R", response.body().toString())
                liveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                liveData.postValue(null)
            }

        })
    }

    fun getMovieTrailer(id: String, trailersList: MutableLiveData<Trailer>) {
        retrofitServiceInstance.getTrailer(id).enqueue(object : Callback<Trailer> {
            override fun onResponse(call: Call<Trailer>, response: Response<Trailer>) {
                val result = response.body() as Trailer
                var pureTrailer: Trailer? = null
                var resultX: ArrayList<ResultX>? = null
                result.results.forEach {
                    if (it.type == "Trailer" && it.official) {
                        if (pureTrailer == null) {
                            resultX = ArrayList<ResultX>()
                            resultX!!.add(ResultX(it.key, it.name, it.type, it.official, it.id))
                            pureTrailer = Trailer(result.id, resultX!!)
                        } else {
                            resultX!!.add(ResultX(it.key, it.name, it.type, it.official, it.id))
                        }
                    }
                }
                Log.d("R/R", pureTrailer.toString())
                trailersList.postValue(pureTrailer)
            }

            override fun onFailure(call: Call<Trailer>, t: Throwable) {
                Log.d("R/R", t.message.toString())
                trailersList.postValue(null)
            }
        })
    }

    fun getYoutubeVideos(key: String, youtubeTrailersList: MutableLiveData<YoutubeTrailerModel>) {
        retrofitServiceInstance.getVideos("https://www.youtube.com/oembed?url=youtube.com/watch?v=${key}&format=json")
            .enqueue(object : Callback<YoutubeTrailerModel> {
                override fun onResponse(
                    call: Call<YoutubeTrailerModel>,
                    response: Response<YoutubeTrailerModel>,
                ) {
                    youtubeTrailersList.postValue(response.body())
                }

                override fun onFailure(call: Call<YoutubeTrailerModel>, t: Throwable) {
                    Log.d("R/R", t.message.toString())
                    youtubeTrailersList.postValue(null)
                }

            })
    }

    fun getMoviesBySearched(page: String, query: String, moviesLiveData: MutableLiveData<Movie>) {
        retrofitServiceInstance.getMoviesBySearched(page, query)
            .enqueue(object : Callback<Movie> {
                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    Log.d("R/W", "data geldi"+response.body().toString())
                    moviesLiveData.postValue(response.body())
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    Log.d("R/W", "data null geldi"+t.message.toString())
                    moviesLiveData.postValue(null)
                }
            })
    }

}