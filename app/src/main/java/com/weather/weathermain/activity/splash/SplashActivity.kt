package com.weather.weathermain.activity.splash

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
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

        try {
            if (ContextCompat.checkSelfPermission(applicationContext,
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (gpsTracker!!.canGetLocation()) {
            locationData = LocationData(gpsTracker!!.latitude, gpsTracker!!.longitude)
        } else {
            gpsTracker!!.showSettingsAlert()
        }

        launchMainActivity()
    }

    private fun launchMainActivity() {
        CurrentWeatherActivity.launch(this, locationData)
        finish()
    }

}