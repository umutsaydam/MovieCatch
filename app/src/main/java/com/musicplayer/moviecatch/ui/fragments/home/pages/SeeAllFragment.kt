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
import com.musicplayer.moviecatch.adapter.SeeAllAdapter
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
    private var query = ""
    private var genreList: List<GenreData>? = null
    private lateinit var seeAllAdapter: SeeAllAdapter

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
            genreList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelableArrayList("genreList", GenreData::class.java)
            } else {
                arguments?.getParcelableArrayList("genreList")
            }
            query = arguments?.getString(Constants.BUNDLE_SEE_ALL_QUERY_KEY).toString()
        }
        binding.seeAllTitleTxt.text = seeAllMovieKey
        viewModel.setMovieKey(seeAllMovieKey)

        seeAllAdapter = SeeAllAdapter(this)

        initRecycler()

        viewModel.getObserverLiveData().observe(viewLifecycleOwner) {
            if (it != null) {
                Log.d("R/S", it.results.toString())
                seeAllAdapter.setLists(it.results, genreList!!)
            } else {
                Log.d("R/S", "null")
            }
        }

        if (query != ""){
            viewModel.loadMovies("1", query)
        }else{
            viewModel.loadMovies("1")
        }

        binding.backImg.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        return binding.root
    }

    private fun initRecycler() {
        val recycler = binding.seeAllRecycler
        recycler.adapter = seeAllAdapter
    }

    override fun onItemClickListener(movie: Result, genres: String) {
        val bundle = Bundle()
        Log.d("W8", movie.toString())
        bundle.putSerializable("movie", movie)
        bundle.putString("genres", genres)
        findNavController().navigate(R.id.action_seeAllFragment_to_movieDetailFragment, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}