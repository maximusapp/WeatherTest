package com.weather.weathermain.activity.weathertoday.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.weather.weathermain.data.repository.WeatherRepository

class WeatherOnTodayViewModel(
        private val weatherRepository: WeatherRepository
) : ViewModel() {

    private var goBack: MutableLiveData<Boolean> = MutableLiveData()

    init {
        requestCurrentWeather()
    }

    fun getCurrentWeather() = requestCurrentWeather()

    fun _getBackPressed(): MutableLiveData<Boolean> {
        return goBack
    }

    fun onBackClicked() {
        _getBackPressed().postValue(true)
    }

    private fun requestCurrentWeather() {
        weatherRepository.getCurrentWeatherData()
    }

}