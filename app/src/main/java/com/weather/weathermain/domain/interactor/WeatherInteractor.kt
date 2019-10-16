package com.weather.weathermain.domain.interactor

import com.weather.weathermain.data.WeatherOnToday
import com.weather.weathermain.domain.repository.IWeatherRepository
import io.reactivex.Single

interface IWeatherInteractor {
    companion object {
        fun getInstance(weather: IWeatherRepository = IWeatherRepository.getInstance()) = WeatherInteractor(weather)
    }

    fun getCurrentWeatherData(latitude: Double, longitude: Double, units: String, appid: String): Single<WeatherOnToday>

}

class WeatherInteractor (private val weatherRepository: IWeatherRepository):
        IWeatherInteractor {
    override fun getCurrentWeatherData(latitude: Double, longitude: Double, units: String, appid: String): Single<WeatherOnToday> {
        return weatherRepository.getCurrentWeatherData(latitude, longitude, units, appid)
    }
}