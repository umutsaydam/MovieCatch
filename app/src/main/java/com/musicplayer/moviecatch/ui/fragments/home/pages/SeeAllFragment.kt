package com.musicplayer.moviecatch.ui.fragments.home.pages

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.musicplayer.moviecatch.R
import com.musicplayer.moviecatch.adapter.MovieAdapter
import com.musicplayer.moviecatch.adapter.RecentMovieAdapter
import com.musicplayer.moviecatch.databinding.FragmentSeeAllBinding
import com.musicplayer.moviecatch.di.dao.GenreData
import com.musicplayer.moviecatch.models.Result
import com.musicplayer.moviecatch.util.Constants
import com.musicplayer.moviecatch.util.OnItemClickListener
import com.musicplayer.moviecatch.viewmodel.SeeAllViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeAllFragment : Fragment(), OnItemClickListener {
    private var _binding: FragmentSeeAllBinding? = null
    private val binding get() = _binding!!
    private var seeAllMovieKey = ""
    private var genreList: List<GenreData>? = null
    val movieAdapter = MovieAdapter(false, this)
    val recentMovieAdapter = RecentMovieAdapter(false, this)

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory)[SeeAllViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSeeAllBinding.inflate(inflater, container, false)


        if (arguments != null) {
            seeAllMovieKey = arguments?.getString(Constants.BUNDLE_SEE_ALL_MOVIE_KEY).toString()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                genreList = arguments?.getParcelableArrayList("genreList", GenreData::class.java)
            }else{
                genreList = arguments?.getParcelableArrayList("genreList")
            }
        }
        binding.seeAllTitleTxt.text = seeAllMovieKey

        initRecycler()

        viewModel.getObserverLiveData().observe(viewLifecycleOwner) {
            if (it != null) {
                when (seeAllMovieKey) {
                    Constants.BUNDLE_SEE_ALL_POPULAR_KEY -> {
                        movieAdapter.setLists(it.results, genreList!!)
                    }

                    Constants.BUNDLE_SEE_ALL_RECENT_KEY -> {
                        recentMovieAdapter.setLists(it.results, genreList!!)
                    }
                }
            } else {
                Log.d("R/S", "null")
            }
        }

        viewModel.loadMovies("1")
        return binding.root
    }

    private fun initRecycler() {
        val recycler = binding.seeAllRecycler
        when (seeAllMovieKey) {
            Constants.BUNDLE_SEE_ALL_POPULAR_KEY -> {
                recycler.adapter = movieAdapter
                viewModel.setMovieKey(seeAllMovieKey)
            }

            Constants.BUNDLE_SEE_ALL_RECENT_KEY -> {
                recycler.adapter = recentMovieAdapter
                viewModel.setMovieKey(seeAllMovieKey)
            }
        }
    }

    override fun onItemClickListener(movie: Result, genres: String) {
        val bundle = Bundle()

        bundle.putSerializable("movie", movie)
        bundle.putString("genres", genres)
        findNavController().navigate(R.id.action_seeAllFragment_to_movieDetailFragment, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}