package com.musicplayer.moviecatch.ui.fragments.home.pages

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.musicplayer.moviecatch.R
import com.musicplayer.moviecatch.adapter.TrailerAdapter
import com.musicplayer.moviecatch.databinding.FragmentMovieDetailBinding
import com.musicplayer.moviecatch.models.Result
import com.musicplayer.moviecatch.models.ResultX
import com.musicplayer.moviecatch.viewmodel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private var movie: Result? = null
    private var genres: String? = null

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory)[MovieDetailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)

        getDatas()

        initUI()

        return binding.root
    }

    private fun getDatas() {
        if (arguments != null) {
            movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getSerializable("movie", Result::class.java)!!
            } else {
                arguments?.getSerializable("movie") as Result
            }
            genres = arguments?.getString("genres").toString()
        }
    }

    private fun initUI() {
        if (movie != null && genres != null) {
            binding.collapsingToolbar.title = " "
            binding.collapsingToolbar

            binding.movieTitle.text = movie!!.title
            binding.txtVoteAverage.text = movie!!.vote_average.toString()
            binding.genres.text = genres
            binding.releaseDateTxt.text = movie!!.release_date
            binding.overviewTxt.text = movie!!.overview

            Glide.with(binding.movieImg)
                .load("https://image.tmdb.org/t/p/w342/" + movie!!.poster_path)
                .into(binding.movieImg)

            val trailerAdapter = TrailerAdapter()
            binding.videoRecycler.adapter = trailerAdapter

            viewModel.getObserverTrailerList().observe(viewLifecycleOwner) {
                if (it != null) {
                    Toast.makeText(context, it.results.toString(), Toast.LENGTH_SHORT).show()
                    loadVideosFromYoutube(it.results)
                } else {
                    Log.d("R/R", "null")
                }
            }

            viewModel.getObserverYoutubeTrailersList().observe(viewLifecycleOwner) {
                if (it != null) {
                    trailerAdapter.addItems(it)
                } else {
                    Log.d("R/R", "null")
                }
            }

            loadTrailers()

            binding.backImg.setOnClickListener {
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
        }
    }

    private fun loadVideosFromYoutube(results: List<ResultX>) {
        CoroutineScope(Dispatchers.IO).launch {
            results.forEach {
                viewModel.loadVideos(it.key)
            }
        }
    }

    private fun loadTrailers() {
        viewModel.loadTrailers(movie?.id.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}