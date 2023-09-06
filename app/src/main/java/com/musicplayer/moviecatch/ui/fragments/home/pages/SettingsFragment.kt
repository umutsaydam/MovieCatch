package com.musicplayer.moviecatch.ui.fragments.home.pages

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.musicplayer.moviecatch.R
import com.musicplayer.moviecatch.databinding.FragmentSettingsBinding
import com.musicplayer.moviecatch.prefs.SessionManager
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
        }
        binding.langTxt.text = resources.getStringArray(R.array.langs)[session.getAppLang()]
    }

    private fun initListeners() {
        val switchTheme = binding.switchTheme
        binding.themeLinear.setOnClickListener {
            switchTheme.toggle()
            changeTheme(switchTheme.isChecked)
        }

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            changeTheme(isChecked)
        }

        binding.langLinear.setOnClickListener {
            showPopUp()
        }
    }

    private fun showPopUp() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(resources.getString(R.string.choose_lang))
        builder.setItems(resources.getStringArray(R.array.langs)) { _, which ->
            session.setAppLang(requireContext(), resources.displayMetrics, which)
            requireActivity().apply {
                finish()
                startActivity(intent)
            }
        }
        builder.show()
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