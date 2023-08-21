package com.musicplayer.moviecatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.musicplayer.moviecatch.adapter.MovieAdapter
import com.musicplayer.moviecatch.viewmodel.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var adapter: MovieAdapter

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory)[HomePageViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MovieAdapter()
        val recycler = findViewById<RecyclerView>(R.id.recyclerPopularMv)
        recycler.adapter = adapter


        viewModel.getObserverLiveData().observe(this) {
            if (it != null) {
                adapter.setList(it.results)
            }else{
                Log.d("R/T", "null geldi")
            }
        }

        viewModel.loadPopularData("11")
    }
}