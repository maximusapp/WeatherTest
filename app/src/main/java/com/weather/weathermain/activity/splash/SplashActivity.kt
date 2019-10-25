package com.weather.weathermain.activity.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.weather.weathermain.R
import com.weather.weathermain.activity.weathertoday.CurrentWeatherActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        launchMainActivity()
    }

    private fun launchMainActivity() {
        CurrentWeatherActivity.launch(this)
        finish()
    }

}