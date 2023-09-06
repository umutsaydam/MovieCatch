package com.musicplayer.moviecatch.prefs

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.util.DisplayMetrics
import com.musicplayer.moviecatch.util.Constants
import java.util.Locale
import javax.inject.Inject

class SessionManager @Inject constructor(private val sharedPreferences: SharedPreferences) {
    private val availableLocales = listOf(
        Locale("TR"), // Türkçe
        Locale("EN"), // İngilizce
    )

    fun getTheme() = sharedPreferences.getBoolean(Constants.THEME_KEY, false)

    fun setTheme(value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(Constants.THEME_KEY, value)
        editor.apply()
    }

    fun getIsFirstRun() = sharedPreferences.getBoolean(Constants.FIRST_RUN_KEY, true)

    fun setIsFirstRun(value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(Constants.FIRST_RUN_KEY, value)
        editor.apply()
    }

    fun getAppLang() = sharedPreferences.getInt(Constants.APP_LANG, 0)

    fun setAppLang(requireContext: Context, displayMetrics: DisplayMetrics,which: Int){
        val editor = sharedPreferences.edit()
        editor.putInt(Constants.APP_LANG, which)
        editor.apply()

        Locale.setDefault(availableLocales[which])
        val config = Configuration()
        config.setLocale(availableLocales[which])
        requireContext.createConfigurationContext(config)
        requireContext.resources.updateConfiguration(config, displayMetrics)
    }
}