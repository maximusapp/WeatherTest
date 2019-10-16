package com.weather.weathermain.data.repository

import com.weather.weathermain.data.WeatherOnToday
import com.weather.weathermain.data.network.service.WeatherService
import com.weather.weathermain.domain.repository.IWeatherRepository
import io.reactivex.Single

class WeatherRepository(private val weatherService: WeatherService = WeatherService.get()): IWeatherRepository {

    override fun getCurrentWeatherData(latitude: Double, longitude: Double, units: String, appid: String): Single<WeatherOnToday> {
        return weatherService.getWeather(latitude, longitude, units, appid)
                .map { it }
    }

}