package com.musicplayer.moviecatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.musicplayer.moviecatch.di.retrofit.RetrofitRepository
import com.musicplayer.moviecatch.models.Trailer
import com.musicplayer.moviecatch.models.YoutubeTrailerModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val repository: RetrofitRepository) :
    ViewModel() {

    var trailersList: MutableLiveData<Trailer> = MutableLiveData()
    var youtubeTrailersList: MutableLiveData<YoutubeTrailerModel> = MutableLiveData()

    fun getObserverTrailerList(): MutableLiveData<Trailer>{
        return trailersList
    }

    fun loadTrailers(id: String){
        repository.getMovieTrailer(id, trailersList)
    }

    fun getObserverYoutubeTrailersList(): MutableLiveData<YoutubeTrailerModel>{
        return youtubeTrailersList
    }

    fun loadVideos(key: String){
        repository.getYoutubeVideos(key, youtubeTrailersList)
    }
}