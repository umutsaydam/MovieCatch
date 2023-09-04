package com.musicplayer.moviecatch.ui.fragments.home.pages

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.musicplayer.moviecatch.R
import com.musicplayer.moviecatch.adapter.FavMovieAdapter
import com.musicplayer.moviecatch.databinding.FragmentFavoriteBinding
import com.musicplayer.moviecatch.di.dao.GenreDB.GenreData
import com.musicplayer.moviecatch.models.Result
import com.musicplayer.moviecatch.util.OnItemClickListener
import com.musicplayer.moviecatch.viewmodel.FavMovieViewModel
import com.musicplayer.moviecatch.viewmodel.GenreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(), OnItemClickListener {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favMovieAdapter: FavMovieAdapter
    private var genreList: List<GenreData> = listOf()


    private val favMovieViewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory)[FavMovieViewModel::class.java]
    }

    private val genreViewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory)[GenreViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        initRecycler()

        setObservers()

        binding.swipeLayout.setOnRefreshListener {
            favMovieViewModel.refreshData()
        }

        return binding.root
    }

    private fun setObservers() {
        favMovieViewModel.getObserverFavMovies().observe(viewLifecycleOwner) {
            if (it != null) {
                favMovieAdapter.setFavMovieList(it, genreList)
                binding.swipeLayout.isRefreshing = false
            } else {
                Log.d("R10/Q", "null geliyor")
            }
        }

        genreViewModel.getRecordsObserver().observe(viewLifecycleOwner) {
            if (it != null) {
                genreList = it
                fetchFavMovies()
            } else {
                Log.d("R10/Q", "genre null")
            }
        }
    }

    private fun fetchFavMovies() {
        favMovieViewModel.loadAllData()
    }

    private fun initRecycler() {
        favMovieAdapter = FavMovieAdapter(this)
        binding.favMovieRecycler.adapter = favMovieAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClickListener(movie: Result, genres: String) {
        val bundle = Bundle()

        bundle.putSerializable("movie", movie)
        bundle.putString("genres", genres)
        findNavController().navigate(R.id.action_favoriteFragment_to_movieDetailFragment, bundle)
    }

}