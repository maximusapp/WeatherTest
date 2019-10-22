package com.weather.weathermain.activity.weathertoday.viewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.weather.weathermain.data.WeatherOnTodayResponse
import com.weather.weathermain.data.repository.WeatherRemoteRepository

class WeatherOnTodayViewModel: ViewModel() {

    private var goBack: MutableLiveData<Boolean> = MutableLiveData()
    private var currentWeather: MutableLiveData<WeatherOnTodayResponse> = MutableLiveData()
    private val weatherRepository = WeatherRemoteRepository()

    private val data: MutableLiveData<String> = MutableLiveData()

    fun _getCurrentWeather(): MutableLiveData<WeatherOnTodayResponse>  { return currentWeather }
    fun _getBackPressed(): MutableLiveData<Boolean> { return goBack }
    fun _getDataOk(): MutableLiveData<String> { return data }
    fun _getDatFail(): MutableLiveData<String> { return data }

    fun onBackClicked() {
        _getBackPressed().postValue(true)
    }

    fun getDataOk() {
        _getDataOk().postValue("OK")
    }

    fun getDataFail() {
        _getDatFail().postValue("FAIL")
    }

     @SuppressLint("CheckResult")
     fun requestCurrentWeather(lat: Double, lon: Double, units: String, appid: String) {
        weatherRepository.getCurrentWeatherData(lat, lon, units, appid)
                .subscribe({_getDataOk()},{ getDataFail()})
    }

}