package com.weather.weathermain.activity.main.mainviewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.weather.weathermain.data.WeatherForecast
import com.weather.weathermain.data.repository.WeatherRemoteRepository

class MainActivityViewModel : ViewModel() {

    private val weatherRepository = WeatherRemoteRepository()

    private var forecastWekek: MutableLiveData<WeatherForecast> = MutableLiveData()
    private val data: MutableLiveData<String> = MutableLiveData()

    fun _getDataFail(): MutableLiveData<String> {
        return data
    }

    fun _getForecastWeek(): MutableLiveData<WeatherForecast> {
        return forecastWekek
    }

    fun getDataFail(error: Unit) {
        _getDataFail().postValue(error.toString())
    }

    @SuppressLint("CheckResult")
    fun requestForecastWeek(lat: Double, lon: Double, units: String, appid: String) {
        weatherRepository.getForecastWeek(lat, lon, units, appid)
                .subscribe({_getForecastWeek().postValue(it)},{getDataFail(it.printStackTrace())})
    }

}