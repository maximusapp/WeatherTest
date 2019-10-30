package com.weather.weathermain.di

import com.weather.weathermain.data.repository.WeatherRemoteRepository
import dagger.Module
import dagger.Provides

@Module
class WeatherRemoteRepositoryModule {
    @Provides
    fun providesWeatherRemoteRepository(): WeatherRemoteRepository{
        return WeatherRemoteRepository()
    }
}