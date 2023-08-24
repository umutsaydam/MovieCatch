package com.musicplayer.moviecatch.prefs

import android.content.SharedPreferences
import com.musicplayer.moviecatch.util.Constants
import javax.inject.Inject

class SessionManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

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
}