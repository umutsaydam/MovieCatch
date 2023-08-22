package com.musicplayer.moviecatch.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.musicplayer.moviecatch.R
import com.musicplayer.moviecatch.databinding.FragmentFavoriteBinding
import com.musicplayer.moviecatch.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
      override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
          _binding = FragmentSplashBinding.inflate(inflater, container, false)

          return binding.root
      }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}