package com.musicplayer.moviecatch.Paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.musicplayer.moviecatch.di.retrofit.RetrofitRepository
import com.musicplayer.moviecatch.models.Movie
import com.musicplayer.moviecatch.models.Result
import com.musicplayer.moviecatch.util.Constants
import kotlin.Exception

class MoviePagingSource(
    private val repository: RetrofitRepository,
    private val seeAllMovieKey: String,
) :
    PagingSource<Int, Result>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val currentPage = params.key ?: 1
            Log.d("R8/W", "$currentPage: current page1")

            val responseData = getMovies(currentPage)

            if (responseData.isNotEmpty()) {
                Log.d("R8/W", "if")
                LoadResult.Page(
                    data = responseData,
                    prevKey = if (currentPage == 1) null else -1,
                    nextKey = currentPage.plus(1)
                )
            } else {
                Log.d("R8/W", "else")
                LoadResult.Page(
                    data = responseData,
                    prevKey = if (currentPage == 1) null else -1,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            Log.d("R8/W", e.message.toString() + "* 36 MoviePaging")
            LoadResult.Error(e)
        }
    }

    private suspend fun getMovies(currentPage: Int): MutableList<Result> {
        var response: MutableList<Movie> = mutableListOf()

        when (seeAllMovieKey) {
            Constants.BUNDLE_SEE_ALL_POPULAR_KEY -> response =
                repository.getPopularMovies2(currentPage.toString())

            Constants.BUNDLE_SEE_ALL_RECENT_KEY -> response =
                repository.getRecentMovies2(currentPage.toString())
        }

        val responseData = mutableListOf<Result>()
        if (response.isNotEmpty()) {
            responseData.addAll(response[0].results)
        }
        return responseData
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}