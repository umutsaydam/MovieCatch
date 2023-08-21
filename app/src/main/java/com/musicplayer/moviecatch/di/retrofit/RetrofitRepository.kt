package com.musicplayer.moviecatch.di.retrofit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.musicplayer.moviecatch.models.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RetrofitRepository @Inject constructor(private val retrofitServiceInstance: RetrofitServiceInstance) {

    fun getPopularMovies(page: String, liveData: MutableLiveData<Movie>) {
        retrofitServiceInstance.getPopularVideos(page).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                Log.d("R/T", response.body().toString()+"**"+response.code().toString())
                liveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.d("R/T", t.message.toString())
                liveData.postValue(null)
            }

        })
    }

}