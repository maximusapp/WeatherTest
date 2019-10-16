package com.weather.weathermain

import android.app.Application
import android.support.v7.app.AppCompatDelegate

class WeatherApplication : Application() {

    init { AppCompatDelegate.setCompatVectorFromResourcesEnabled(true) }

    companion object {
        lateinit var instance: WeatherApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}