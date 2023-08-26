package com.musicplayer.moviecatch.util

import com.musicplayer.moviecatch.models.Result

interface OnItemClickListener {
    fun onItemClickListener(movie: Result, genres: String)
}