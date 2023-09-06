package com.musicplayer.moviecatch.ui.fragments.home.pages

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.musicplayer.moviecatch.databinding.FragmentSettingsBinding
import com.musicplayer.moviecatch.prefs.SessionManager
import com.musicplayer.moviecatch.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var session: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        initListeners()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (session.getTheme()) {
            binding.switchTheme.isChecked = true
            Log.d("R/WT", "(session.getTheme()")
        }
    }

    private fun initListeners() {
        val switchTheme = binding.switchTheme
        binding.themeLinear.setOnClickListener {
            switchTheme.toggle()
            changeTheme(switchTheme.isChecked)
            Log.d("R/WT", "themeLinear")
        }

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            changeTheme(isChecked)
            Log.d("R/WT", "switchTheme")
        }

        binding.langLinear.setOnClickListener {
            Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
            Log.d("R/WT", "langLinear")
        }
    }

    private fun changeTheme(isChecked: Boolean) {
        Log.d("R/WT", "changeTheme")
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        session.setTheme(isChecked)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}