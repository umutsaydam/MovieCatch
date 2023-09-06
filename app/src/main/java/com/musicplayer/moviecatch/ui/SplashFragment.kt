package com.musicplayer.moviecatch.ui


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.musicplayer.moviecatch.R
import com.musicplayer.moviecatch.databinding.FragmentSplashBinding
import com.musicplayer.moviecatch.prefs.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        Handler(Looper.getMainLooper()).postDelayed({
            if (sessionManager.getIsFirstRun()) {
                findNavController().navigate(R.id.action_splashFragment_to_appIntroFragment)
            } else {
                val appLang = sessionManager.getAppLang()
                if (appLang != 1){
                    sessionManager.setAppLang(
                        requireContext(),
                        resources.displayMetrics,
                        appLang
                    )
                }
                findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
            }
        }, 3000)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}