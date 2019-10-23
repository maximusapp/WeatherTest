package com.weather.weathermain.data.repository

import com.weather.weathermain.data.WeatherOnTodayResponse
import com.weather.weathermain.data.network.service.WeatherService
import io.reactivex.Single

class WeatherRemoteRepository(private val weatherService: WeatherService = WeatherService.get()) {
    fun getCurrentWeatherData(latitude: Double, longitude: Double, units: String, appid: String): Single<WeatherOnTodayResponse> =
            weatherService.getWeather(latitude, longitude, units, appid)
                    .map { it }
}