package com.musicplayer.moviecatch.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.musicplayer.moviecatch.R
import com.musicplayer.moviecatch.databinding.FragmentMainBinding
import com.musicplayer.moviecatch.prefs.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var session: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        if (session.getIsFirstRun()) {
            session.setIsFirstRun(false)
        }

        setupTabBar()

        return binding.root
    }

    private fun setupTabBar() {
        binding.bottomNavBar.setItemSelected(R.id.nav_home, true)
        binding.bottomNavBar.setOnItemSelectedListener {
            when (it) {
                R.id.nav_home -> childFragmentManager.primaryNavigationFragment?.findNavController()
                    ?.navigate(R.id.homeFragment)

                R.id.nav_favorites -> childFragmentManager.primaryNavigationFragment?.findNavController()
                    ?.navigate(R.id.favoriteFragment)

                R.id.nav_settings -> childFragmentManager.primaryNavigationFragment?.findNavController()
                    ?.navigate(R.id.settingsFragment)

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}