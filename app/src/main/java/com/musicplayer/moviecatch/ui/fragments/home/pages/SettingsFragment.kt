package com.musicplayer.moviecatch.ui.fragments.home.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.musicplayer.moviecatch.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        initListeners()

        return binding.root
    }

    private fun initListeners() {
        val switchTheme = binding.switchTheme
        switchTheme.setOnClickListener {
            switchTheme.toggle()

            changeTheme(switchTheme.isChecked)
        }

        switchTheme.setOnCheckedChangeListener { _, _ ->
            changeTheme(switchTheme.isChecked)
        }

        binding.langLinear.setOnClickListener {
            Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun changeTheme(isChecked: Boolean) {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}