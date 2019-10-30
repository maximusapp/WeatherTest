package com.weather.weathermain.di

import dagger.Module
import dagger.Provides

@Module
class CurrentWeatherViewModule {
    @Provides
    fun provideCurrentWeatherViewModel(): CurrentWeatherViewModule {
        return CurrentWeatherViewModule()
    }
}