package com.weather.weathermain.activity.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.weather.weathermain.GPSTracker
import com.weather.weathermain.R
import com.weather.weathermain.activity.weathertoday.CurrentWeatherActivity
import com.weather.weathermain.data.LocationData

class SplashActivity : AppCompatActivity() {

    private var gpsTracker: GPSTracker? = null

    private lateinit var locationData: LocationData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        gpsTracker = GPSTracker(this@SplashActivity)
        if (gpsTracker!!.canGetLocation() && gpsTracker != null) {
            launchMainActivity()
        } else {
            gpsTracker!!.showSettingsAlert()
        }

    }

    private fun launchMainActivity() {
        locationData = LocationData(gpsTracker!!.latitude, gpsTracker!!.longitude)
        CurrentWeatherActivity.launch(this, locationData)
        finish()
    }

}