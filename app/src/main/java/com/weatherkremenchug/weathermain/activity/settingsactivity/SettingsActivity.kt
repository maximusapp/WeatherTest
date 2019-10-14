package com.weatherkremenchug.weathermain.activity.settingsactivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.widget.Switch

import com.weatherkremenchug.weathermain.activity.mainactivity.MainActivity
import com.weatherkremenchug.weathermain.R

import java.util.Objects

class SettingsActivity : AppCompatActivity() {
    companion object {
        fun launch (context: Context) {
            val launcher = Intent(context, SettingsActivity::class.java)
            context.startActivity(launcher)
        }

        val APP_PREFERENCES = "settings"
    }

    lateinit var sharedPreferences: SharedPreferences
    lateinit var switch_wi_fi: Switch
    lateinit var switch_update_start: Switch
    lateinit var switch_ask_when_exit: Switch

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        MainActivity.launch(this)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_settings)
        setSupportActionBar(toolbar)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull<ActionBar>(supportActionBar).setDisplayHomeAsUpEnabled(true)
            Objects.requireNonNull<ActionBar>(supportActionBar).setTitle("Настройки")
        }

        val window = this.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = this.resources.getColor(R.color.colorGray)
        }

        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        switch_wi_fi = findViewById(R.id.switch_wi_fi)
        switch_update_start = findViewById(R.id.switch_update_start)
        switch_ask_when_exit = findViewById(R.id.switch_ask_when_exit)

        switch_wi_fi.setOnClickListener {
            val editorWifi = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).edit()
            editorWifi.putBoolean("switch_wi_fi", switch_wi_fi.isChecked)
            editorWifi.apply()
        }

        switch_update_start.setOnClickListener {
            val editorUpdateStart = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).edit()
            editorUpdateStart.putBoolean("switch_update_start", switch_update_start.isChecked)
            editorUpdateStart.apply()
        }

        switch_ask_when_exit.setOnClickListener {
            val editorAskExit = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).edit()
            editorAskExit.putBoolean("switch_ask_when_exit", switch_ask_when_exit.isChecked)
            editorAskExit.apply()
        }

        val switchWifiState = sharedPreferences.getBoolean("switch_wi_fi", false)
        val switchUpdateOnStartState = sharedPreferences.getBoolean("switch_update_start", false)
        val switchAskExitState = sharedPreferences.getBoolean("switch_ask_when_exit", false)

        // Handle wi_fi switch.
        when {
            switchWifiState -> switch_wi_fi.isChecked = true
            else -> switch_wi_fi.isChecked = false
        }

        // Handle update switch.
        when {
            switchUpdateOnStartState -> switch_update_start.isChecked = true
            else -> switch_update_start.isChecked = false
        }

        // Handle exit switch.
        when {
            switchAskExitState -> switch_ask_when_exit.isChecked = true
            else -> switch_ask_when_exit.isChecked = false
        }
    }
}
//107