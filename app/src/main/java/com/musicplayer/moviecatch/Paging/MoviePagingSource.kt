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
    private val searchQuery: String = "",
) :
    PagingSource<Int, Result>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val currentPage = params.key ?: 1
            Log.d("R8/W", "$currentPage: current page1")

            val responseData = getMovies(currentPage)


            if (responseData != null && responseData.isEmpty()){
                Log.d("R8/W", "if")
                LoadResult.Page(
                    data = responseData!!,
                    prevKey = if (currentPage == 1) null else -1,
                    nextKey = currentPage.plus(1)
                )
            }else{
                Log.d("R8/Q", "else")
                LoadResult.Page(
                    data = responseData!!,
                    prevKey = if (currentPage == 1) null else -1,
                    nextKey = if (responseData == null) null else -1
                )
            }
        } catch (e: Exception) {
            Log.d("R8/W", e.message.toString() + "* 42 MoviePaging")
            LoadResult.Error(e)
        }
    }

    private suspend fun getMovies(currentPage: Int): List<Result>? {
        var response: Movie? = null

        when (seeAllMovieKey) {
            Constants.BUNDLE_SEE_ALL_POPULAR_KEY -> response =
                repository.getPopularMovies2(currentPage.toString())

            Constants.BUNDLE_SEE_ALL_RECENT_KEY -> response =
                repository.getRecentMovies2(currentPage.toString())

            Constants.BUNDLE_SEE_ALL_QUERY_KEY -> response =
                repository.getMoviesBySearched2(currentPage.toString(), searchQuery)
        }

        var responseData: List<Result>? = null
        if (response != null) {
            Log.d("R8/Q", response.page.toString())
            responseData = response.results
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