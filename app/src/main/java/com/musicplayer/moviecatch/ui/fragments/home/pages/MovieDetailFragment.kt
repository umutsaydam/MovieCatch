package com.musicplayer.moviecatch.ui.fragments.home.pages

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.musicplayer.moviecatch.databinding.FragmentMovieDetailBinding
import com.musicplayer.moviecatch.models.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private var movie: Result? = null
    private var genres: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
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
                .load("https://image.tmdb.org/t/p/w342/"+movie!!.poster_path)
                .into(binding.movieImg)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}