package com.weather.weathermain.di

import com.weather.weathermain.activity.weathertoday.CurrentWeatherActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [WeatherRemoteRepositoryModule::class])

@Singleton
interface AppComponent {

    fun inject(activity: CurrentWeatherActivity)

}