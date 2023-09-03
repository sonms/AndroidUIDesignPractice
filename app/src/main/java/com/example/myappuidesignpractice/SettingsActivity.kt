package com.example.myappuidesignpractice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat

class SettingsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        private var switch : SwitchPreferenceCompat? = null
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            switch = findPreference("sync")
            switch!!.setOnPreferenceChangeListener { preference, newValue ->
                var isChecked = false
                if (newValue as Boolean) {
                    isChecked = newValue
                }
                if (isChecked) {
                    println("tr")
                } else {
                   println("fa")
                }
                return@setOnPreferenceChangeListener true
            }


        }
    }
}