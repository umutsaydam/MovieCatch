package com.musicplayer.moviecatch.Paging

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.musicplayer.moviecatch.di.retrofit.RetrofitRepository
import com.musicplayer.moviecatch.models.Movie
import com.musicplayer.moviecatch.models.Result
import kotlin.Exception

class MoviePagingSource(private val repository: RetrofitRepository) :
    PagingSource<Int, Result>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            Log.d("R8/W", params.key.toString() + ": params.key")
            val currentPage = params.key ?: 1
            Log.d("R8/W", currentPage.toString() + ": current page1")
            val response: MutableList<Movie> =
                repository.getPopularMovies2(currentPage.toString())
            val responseData = mutableListOf<Result>()
            if (response.isEmpty()){
                Log.d("R8/W", "response null")
            }
            if (response.isNotEmpty() && response[0].results.isNotEmpty()) {
                Log.d("R8/W", "if")
                responseData.addAll(response[0].results)
                LoadResult.Page(
                    data = responseData,
                    prevKey = if (currentPage == 1) null else -1,
                    nextKey = currentPage.plus(1)
                )
            } else {
                Log.d("R8/W", "else")
               /* responseData.add(Result(backdrop_path="8pjWz2lt29KyVGoq1mXYu6Br7dE.jpg",
                    genre_ids= listOf(28, 878, 27), id=615656, overview="An exploratory dive into the deepest", popularity=7621.501, poster_path="4m1Au3YkjqsxF8iwQy0fPYSxE0h.jpg",
                    release_date="2023-08-0", title="Meg", vote_average=7.0, genrestringTr="", genrestring=""))*/
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

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}