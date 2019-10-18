package com.weather.weathermain.data.repository

import com.weather.weathermain.data.WeatherOnTodayEntity
import com.weather.weathermain.data.network.service.WeatherService
import io.reactivex.Single

class WeatherRemoteRepository(private val weatherService: WeatherService = WeatherService.get()): WeatherRepository {
    override fun getCurrentWeatherData(latitude: Double, longitude: Double, units: String, appid: String): Single<WeatherOnTodayEntity> {
        return weatherService.getWeather(latitude, longitude, units, appid)
    }
}