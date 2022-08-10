package com.penatabahasa.habittracker.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.penatabahasa.habittracker.R
import com.penatabahasa.habittracker.databinding.SettingsActivityBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: SettingsActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.settings_activity)
        supportActionBar?.hide()

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
            binding.apply {
                ivBack.setOnClickListener {
                    onBackPressed()
                }
            }
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val themePreference = findPreference<ListPreference>(getString(R.string.pref_key_dark))
            themePreference?.setOnPreferenceChangeListener { _, newValue ->
                when (newValue) {
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
                true
            }
        }

        private fun updateTheme(mode: Int): Boolean {
            AppCompatDelegate.setDefaultNightMode(mode)
            requireActivity().recreate()
            return true
        }
    }
}