package com.example.myappuidesignpractice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.example.myappuidesignpractice.fragment.FirstFragment

//원래 쓰던 setting방식과는 다른 replace방식
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
        private var prefer : Preference? = null

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            if (rootKey == null) {
                // Preference 객체들 초기화
                prefer = findPreference("test")
            }
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

            prefer?.setOnPreferenceClickListener {
                val fm = requireActivity().supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(R.id.settings, FirstFragment()).commit()


                return@setOnPreferenceClickListener true
            }

        }
    }
}
