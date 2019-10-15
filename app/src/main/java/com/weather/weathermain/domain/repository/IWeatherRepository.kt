package com.weather.weathermain.domain.repository

import com.weather.weathermain.data.repository.WeatherRepository

interface IWeatherRepository {
    companion object {
        fun getInstance(): IWeatherRepository = WeatherRepository()
    }
}