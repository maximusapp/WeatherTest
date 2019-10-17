package com.weather.weathermain.domain.repository

import com.weather.weathermain.data.WeatherOnTodayEntity
import com.weather.weathermain.data.repository.WeatherRepository
import io.reactivex.Single

interface IWeatherRepository {
    companion object {
        fun getInstance(): IWeatherRepository = WeatherRepository()
    }

    fun getCurrentWeatherData(latitude: Double, longitude: Double, units: String, appid: String): Single<WeatherOnTodayEntity>

}