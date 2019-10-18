package com.weather.weathermain.activity.weathertoday.viewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.weather.weathermain.data.WeatherOnTodayEntity
import com.weather.weathermain.data.repository.WeatherRepository

class WeatherOnTodayViewModel : ViewModel() {

    private val weatherRepository: WeatherRepository = WeatherRepository()

    private var goBack: MutableLiveData<Boolean> = MutableLiveData()
    private var currentWeather: MutableLiveData<WeatherOnTodayEntity> = MutableLiveData()

    fun _getCurrentWeather(): MutableLiveData<WeatherOnTodayEntity>  { return currentWeather }
    fun _getBackPressed(): MutableLiveData<Boolean> { return goBack }

    fun onBackClicked() {
        _getBackPressed().postValue(true)
    }

    @SuppressLint("CheckResult")
     fun requestCurrentWeather(lat: Double, lon: Double, units: String, appid: String) {
        weatherRepository.getCurrentWeatherData(lat, lon, units, appid)
                .map { _getCurrentWeather().postValue(it) }
    }

}