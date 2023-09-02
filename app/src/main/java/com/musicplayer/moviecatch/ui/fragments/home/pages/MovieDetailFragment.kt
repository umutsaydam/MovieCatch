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
import com.bumptech.glide.Glide
import com.musicplayer.moviecatch.R
import com.musicplayer.moviecatch.adapter.TrailerAdapter
import com.musicplayer.moviecatch.databinding.FragmentMovieDetailBinding
import com.musicplayer.moviecatch.di.dao.FavMovieDB.FavMovieData
import com.musicplayer.moviecatch.models.Result
import com.musicplayer.moviecatch.models.ResultX
import com.musicplayer.moviecatch.viewmodel.FavMovieViewModel
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
    private var genres: String = "dsfsdfdsfdf"

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory)[MovieDetailViewModel::class.java]
    }

    private val favMovieViewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory)[FavMovieViewModel::class.java]
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
        if (this.movie != null) {
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
            setFavImg(favMovieViewModel.isFavMovie(movie!!.id))
            binding.favImg.setOnClickListener {
                val isFavMovie = favMovieViewModel.isFavMovie(movie!!.id)

                if (isFavMovie) {
                    favMovieViewModel.deleteFavMovie(movie!!.id)
                } else {
                    favMovieViewModel.addFavMovie(FavMovieData(
                        0,
                        movie!!.backdrop_path,
                        movie!!.genre_ids,
                        movie!!.id,
                        movie!!.overview,
                        movie!!.popularity,
                        movie!!.poster_path,
                        movie!!.release_date,
                        movie!!.title,
                        movie!!.vote_average,
                        movie!!.genrestringTr,
                        movie!!.genrestring,
                    ))
                }
                setFavImg(!isFavMovie)
            }

            loadTrailers()

            binding.backImg.setOnClickListener {
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
        }
    }

    private fun setFavImg(isFavMovie: Boolean) {
        if (isFavMovie) {
            binding.favImg.setImageResource(R.drawable.ic_heart_fill)
        } else {
            binding.favImg.setImageResource(R.drawable.ic_heart_blank)
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