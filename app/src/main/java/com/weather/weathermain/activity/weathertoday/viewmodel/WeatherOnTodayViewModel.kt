package com.weather.weathermain.activity.weathertoday.viewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.weather.weathermain.data.WeatherOnTodayEntity
import com.weather.weathermain.data.repository.WeatherRepository
import io.reactivex.schedulers.Schedulers

class WeatherOnTodayViewModel : ViewModel() {

    private val weatherRepository: WeatherRepository = WeatherRepository()

    private var goBack: MutableLiveData<Boolean> = MutableLiveData()
    private val currentWeather: MutableLiveData<WeatherOnTodayEntity> = MutableLiveData()

//    private val lat: Double = 45.195666
//    private val lon: Double = 39.254666
//    private val units: String = "metric"
//    private val appid: String = "c3eebf803f44713f50e31a7c5b215a73"

//    init {
//        requestCurrentWeather()
//    }

    //fun refreshCurrentWeather() = requestCurrentWeather()

    fun _getCurrentWeather(): MutableLiveData<WeatherOnTodayEntity>  { return currentWeather }
    fun _getBackPressed(): MutableLiveData<Boolean> { return goBack }

    fun onBackClicked() {
        _getBackPressed().postValue(true)
    }

    @SuppressLint("CheckResult")
     fun requestCurrentWeather(lat: Double, lon: Double, units: String, appid: String) {
        weatherRepository.getCurrentWeatherData(lat, lon, units, appid)
                .subscribeOn(Schedulers.io())
                .map { _getCurrentWeather().postValue(it) }
    }

}