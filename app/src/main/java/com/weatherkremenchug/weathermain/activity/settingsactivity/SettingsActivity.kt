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
import com.weatherkremenchug.weathermain.constants.APP_PREFERENCES

import java.util.Objects

class SettingsActivity : AppCompatActivity() {
    companion object {
        fun launch (context: Context) {
            val launcher = Intent(context, SettingsActivity::class.java)
            context.startActivity(launcher)
        }
    }

    private lateinit var sharedPreferences: SharedPreferences
    lateinit var switchWifi: Switch
    lateinit var switchUpdateStart: Switch
    lateinit var switchAskWhenExit: Switch

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
            Objects.requireNonNull<ActionBar>(supportActionBar).title = "Настройки"
        }

        val window = this.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = this.resources.getColor(R.color.colorGray)
        }

        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        switchWifi = findViewById(R.id.switch_wi_fi)
        switchUpdateStart = findViewById(R.id.switch_update_start)
        switchAskWhenExit = findViewById(R.id.switch_ask_when_exit)

        switchWifi.setOnClickListener {
            val editorWifi = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).edit()
            editorWifi.putBoolean("switchWifi", switchWifi.isChecked)
            editorWifi.apply()
        }

        switchUpdateStart.setOnClickListener {
            val editorUpdateStart = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).edit()
            editorUpdateStart.putBoolean("switchUpdateStart", switchUpdateStart.isChecked)
            editorUpdateStart.apply()
        }

        switchAskWhenExit.setOnClickListener {
            val editorAskExit = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).edit()
            editorAskExit.putBoolean("switchAskWhenExit", switchAskWhenExit.isChecked)
            editorAskExit.apply()
        }

        val switchWifiState = sharedPreferences.getBoolean("switchWifi", false)
        val switchUpdateOnStartState = sharedPreferences.getBoolean("switchUpdateStart", false)
        val switchAskExitState = sharedPreferences.getBoolean("switchAskWhenExit", false)

        // Handle wi_fi switch.
        when {
            switchWifiState -> switchWifi.isChecked = true
            else -> switchWifi.isChecked = false
        }

        // Handle update switch.
        when {
            switchUpdateOnStartState -> switchUpdateStart.isChecked = true
            else -> switchUpdateStart.isChecked = false
        }

        // Handle exit switch.
        when {
            switchAskExitState -> switchAskWhenExit.isChecked = true
            else -> switchAskWhenExit.isChecked = false
        }
    }
}
//107