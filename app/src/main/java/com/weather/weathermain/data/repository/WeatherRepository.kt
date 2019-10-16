package com.weather.weathermain.data.repository

import com.weather.weathermain.data.network.service.WeatherService
import com.weather.weathermain.domain.repository.IWeatherRepository

class WeatherRepository(private val weatherService: WeatherService = WeatherService.get()): IWeatherRepository {
}