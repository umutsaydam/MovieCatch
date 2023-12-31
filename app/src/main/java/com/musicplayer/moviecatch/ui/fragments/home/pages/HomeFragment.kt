package com.musicplayer.moviecatch.ui.fragments.home.pages

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.musicplayer.moviecatch.R
import com.musicplayer.moviecatch.adapter.MovieAdapter
import com.musicplayer.moviecatch.adapter.RecentMovieAdapter
import com.musicplayer.moviecatch.databinding.FragmentHomeBinding
import com.musicplayer.moviecatch.di.dao.GenreDB.GenreData
import com.musicplayer.moviecatch.models.Result
import com.musicplayer.moviecatch.util.Constants
import com.musicplayer.moviecatch.util.OnItemClickListener
import com.musicplayer.moviecatch.viewmodel.GenreViewModel
import com.musicplayer.moviecatch.viewmodel.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), OnItemClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MovieAdapter
    private lateinit var recentMovieAdapter: RecentMovieAdapter
    private var genreList: List<GenreData>? = null

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory)[HomePageViewModel::class.java]
    }

    private val genreViewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory)[GenreViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        seeAllListeners()
        initRecyclerViews()

        viewModel.getObserverLiveData(true).observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setLists(it.results, genreList!!)
            } else {
                Log.d("R/T", "null geldi")
            }
        }

        viewModel.getObserverLiveData(false).observe(viewLifecycleOwner) {
            if (it != null) {
                recentMovieAdapter.setLists(it.results, genreList!!)
            } else {
                Log.d("R/T", "NULL GELDI recent")
            }
        }

        genreViewModel.getRecordsObserver().observe(
            viewLifecycleOwner
        ) {
            if (it != null) {
                genreList = it
                fetchMovies()
            }
        }

        return binding.root
    }

    private fun seeAllListeners() {
        val bundle = Bundle()
        binding.popularMoviesLinear.setOnClickListener {
            bundle.putString(
                Constants.BUNDLE_SEE_ALL_MOVIE_KEY,
                Constants.BUNDLE_SEE_ALL_POPULAR_KEY
            )
            bundle.putParcelableArrayList("genreList", ArrayList<Parcelable>(genreList!!))
            findNavController().navigate(R.id.action_homeFragment_to_seeAllFragment, bundle)
        }

        binding.recentMoviesLinear.setOnClickListener {
            bundle.putString(
                Constants.BUNDLE_SEE_ALL_MOVIE_KEY,
                Constants.BUNDLE_SEE_ALL_RECENT_KEY
            )
            bundle.putParcelableArrayList("genreList", ArrayList<Parcelable>(genreList!!))
            findNavController().navigate(R.id.action_homeFragment_to_seeAllFragment, bundle)
        }

        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    bundle.putString(
                        Constants.BUNDLE_SEE_ALL_MOVIE_KEY,
                        Constants.BUNDLE_SEE_ALL_QUERY_KEY
                    )
                    bundle.putString(Constants.BUNDLE_SEE_ALL_QUERY_KEY, query.trim())
                    bundle.putParcelableArrayList("genreList", ArrayList<Parcelable>(genreList!!))
                    findNavController().navigate(R.id.action_homeFragment_to_seeAllFragment, bundle)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun initRecyclerViews() {
        val lmHorizontal =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val lmVertical =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        adapter = MovieAdapter(true, this)
        val recycler = binding.recyclerPopularMv
        recycler.layoutManager = lmHorizontal
        recycler.adapter = adapter

        recentMovieAdapter = RecentMovieAdapter(true, this)
        val recentRecycler = binding.recyclerRecentMv
        recentRecycler.layoutManager = lmVertical
        recentRecycler.adapter = recentMovieAdapter
    }

    private fun fetchMovies() {
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

    override fun onItemClickListener(movie: Result, genres: String) {
        val bundle = Bundle()

        bundle.putSerializable("movie", movie)
        bundle.putString("genres", genres)
        findNavController().navigate(R.id.action_homeFragment_to_movieDetailFragment, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}