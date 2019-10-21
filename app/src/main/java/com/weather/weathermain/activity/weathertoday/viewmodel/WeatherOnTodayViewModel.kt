package com.weather.weathermain.activity.weathertoday.viewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.weather.weathermain.data.WeatherOnTodayEntity
import com.weather.weathermain.data.repository.WeatherRemoteRepository

class WeatherOnTodayViewModel: ViewModel() {

    private var goBack: MutableLiveData<Boolean> = MutableLiveData()
    private var currentWeather: MutableLiveData<WeatherOnTodayEntity> = MutableLiveData()
    private val weatherRepository: WeatherRemoteRepository? = null

    fun _getCurrentWeather(): MutableLiveData<WeatherOnTodayEntity>  { return currentWeather }
    fun _getBackPressed(): MutableLiveData<Boolean> { return goBack }

    fun onBackClicked() {
        _getBackPressed().postValue(true)
    }

    @SuppressLint("CheckResult")
     fun requestCurrentWeather(lat: Double, lon: Double, units: String, appid: String) {
        weatherRepository?.getCurrentWeatherData(lat, lon, units, appid)
                ?.subscribe({_getCurrentWeather().postValue(it)},{ Log.d("ERROR", it.message)})
    }

}