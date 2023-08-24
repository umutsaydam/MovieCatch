package com.musicplayer.moviecatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.musicplayer.moviecatch.di.dao.GenreData
import com.musicplayer.moviecatch.di.dao.GenreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(private val genreRepository: GenreRepository) :
    ViewModel() {

    var allData: MutableLiveData<List<GenreData>> = MutableLiveData()

    init {
        loadRecords()
    }

    fun getRecordsObserver(): MutableLiveData<List<GenreData>> {
        return allData
    }

    fun addGenre(genreData: GenreData) {
        genreRepository.addGenre(genreData)
        loadRecords()
    }

    fun addAllGenres(genresList: List<GenreData>) {
        genreRepository.addAllGenre(genresList)
        loadRecords()
    }


    private fun loadRecords() {
        val list = genreRepository.readAllData
        allData.postValue(list)
    }
}