package com.musicplayer.moviecatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.musicplayer.moviecatch.adapter.MovieAdapter
import com.musicplayer.moviecatch.adapter.RecentMovieAdapter
import com.musicplayer.moviecatch.viewmodel.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var adapter: MovieAdapter
    private lateinit var recentMovieAdapter: RecentMovieAdapter

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory)[HomePageViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerViews()

        viewModel.getObserverLiveData(true).observe(this) {
            if (it != null) {
                adapter.setList(it.results)
            } else {
                Log.d("R/T", "null geldi")
            }
        }

        viewModel.getObserverLiveData(false).observe(this) {
            if (it != null) {
                recentMovieAdapter.setList(it.results)
            } else {
                Log.d("R/T", "NULL GELDI recent")
            }
        }
        fetchMovies()
    }

    private fun initRecyclerViews() {
        val lmHorizontal =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        val lmVertical =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)


        adapter = MovieAdapter()
        val recycler = findViewById<RecyclerView>(R.id.recyclerPopularMv)
        recycler.layoutManager = lmHorizontal
        recycler.adapter = adapter

        recentMovieAdapter = RecentMovieAdapter()
        val recentRecycler = findViewById<RecyclerView>(R.id.recyclerRecentMv)
        recentRecycler.layoutManager = lmVertical
        recentRecycler.adapter = recentMovieAdapter
    }

    fun fetchMovies() {
        CoroutineScope(Dispatchers.IO).launch {
            val fetchPopular: Deferred<Unit> = async {
                viewModel.loadPopularData("1")
            }

            val fetchRecent: Deferred<Unit> = async {
                viewModel.loadRecentData("1")
            }

            fetchPopular.await()
            fetchRecent.await()
        }
    }


}