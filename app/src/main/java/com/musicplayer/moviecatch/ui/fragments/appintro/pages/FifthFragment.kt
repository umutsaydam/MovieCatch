package com.musicplayer.moviecatch.ui.fragments.appintro.pages

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.musicplayer.moviecatch.R
import com.musicplayer.moviecatch.databinding.FragmentFifthBinding
import com.musicplayer.moviecatch.di.dao.GenreDB.GenreData
import com.musicplayer.moviecatch.util.StringHelper
import com.musicplayer.moviecatch.viewmodel.GenreViewModel
import com.musicplayer.moviecatch.viewmodel.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FifthFragment : Fragment() {
    private var _binding: FragmentFifthBinding? = null
    private val binding get() = _binding!!
    private lateinit var genreViewModel: GenreViewModel
    private lateinit var homeViewModel: HomePageViewModel
    private lateinit var stringHelper: StringHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFifthBinding.inflate(inflater, container, false)
        stringHelper = StringHelper()
        val genreList: MutableList<GenreData> = mutableListOf()

        genreViewModel = ViewModelProvider(this).get(GenreViewModel::class.java)
        homeViewModel = ViewModelProvider(this).get(HomePageViewModel::class.java)


        homeViewModel.getObserverGenre().observe(viewLifecycleOwner) {
            if (it != null) {
                for (item in it.genres) {
                    Log.d("R/T", it.genres.toString())
                    genreList.add(
                        GenreData(
                            0,
                            item.id,
                            item.name,
                            stringHelper.getTrName(item.name)
                        )
                    )
                }
                genreViewModel.addAllGenres(genreList)
            } else {
                Log.d("R/T", "null")
            }
        }


        binding.btnNext.setOnClickListener {
            homeViewModel.loadGenreData()
            findNavController().navigate(R.id.action_appIntroFragment_to_mainFragment)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}