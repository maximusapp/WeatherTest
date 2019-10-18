package com.weather.weathermain.data.repository

import com.weather.weathermain.data.WeatherOnTodayEntity
import io.reactivex.Single

interface WeatherRepository {
    fun getCurrentWeatherData(latitude: Double, longitude: Double, units: String, appid: String): Single<WeatherOnTodayEntity>
}