package com.weather.weathermain

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import com.weather.weathermain.di.AppComponent
import com.weather.weathermain.di.DaggerAppComponent

class WeatherApplication : Application() {

    init { AppCompatDelegate.setCompatVectorFromResourcesEnabled(true) }

    companion object {
        lateinit var instance: WeatherApplication

        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initializeDagger()
    }

    private fun initializeDagger() {
        appComponent = DaggerAppComponent.builder().build()
    }

}