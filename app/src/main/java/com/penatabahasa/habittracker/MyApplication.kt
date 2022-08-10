package com.penatabahasa.habittracker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val prefManager =
            androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext)
        when (prefManager.getString(applicationContext.getString(R.string.pref_key_dark), "")) {
            getString(R.string.pref_dark_auto) -> {
                updateTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            getString(R.string.pref_dark_on) -> {
                updateTheme(AppCompatDelegate.MODE_NIGHT_YES)
            }
            getString(R.string.pref_dark_off) -> {
                updateTheme(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun updateTheme(mode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(mode)
        return true
    }
}