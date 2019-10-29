package com.weather.weathermain.activity.weathertoday.viewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.weather.weathermain.R
import com.weather.weathermain.data.WeatherOnTodayResponse
import com.weather.weathermain.data.repository.WeatherRemoteRepository

class CurrentWeatherViewModel: ViewModel() {

    private var icon: MutableLiveData<Int> = MutableLiveData()
    private var currentWeather: MutableLiveData<WeatherOnTodayResponse> = MutableLiveData()
    private val weatherRepository = WeatherRemoteRepository()

    private val data: MutableLiveData<String> = MutableLiveData()

    fun _setCurrentWeather(): MutableLiveData<WeatherOnTodayResponse>  { return currentWeather }
    fun _setWeatherIcon(): MutableLiveData<Int> { return icon }
    fun _getDatFail(): MutableLiveData<String> { return data }

    fun getWeatherIcon(icon: String): String {
       when (icon) {
           "01d" -> _setWeatherIcon().postValue(R.drawable.icon_clear_sky_day)
           "02d" -> _setWeatherIcon().postValue(R.drawable.icon_few_clouds_day)
           "03d" -> _setWeatherIcon().postValue(R.drawable.icon_clouds_day)
           "04d" -> _setWeatherIcon().postValue(R.drawable.icon_clouds_day)
           "50d" -> _setWeatherIcon().postValue(R.drawable.icon_haze_day)
           "09d" -> _setWeatherIcon().postValue(R.drawable.icon_shower_rain)
           "10d" -> _setWeatherIcon().postValue(R.drawable.icon_rain)
           "11d" -> _setWeatherIcon().postValue(R.drawable.icon_storm)
           "13d" -> _setWeatherIcon().postValue(R.drawable.icon_snow)

           "01n" -> _setWeatherIcon().postValue(R.drawable.icon_clear_sky_night)
           "02n" -> _setWeatherIcon().postValue(R.drawable.icon_few_clouds_night)
           "03n" -> _setWeatherIcon().postValue(R.drawable.icon_clouds_day)
           "04n" -> _setWeatherIcon().postValue(R.drawable.icon_clouds_day)
           "50n" -> _setWeatherIcon().postValue(R.drawable.icon_haze_night)
           "09n" -> _setWeatherIcon().postValue(R.drawable.icon_shower_rain)
           "10n" -> _setWeatherIcon().postValue(R.drawable.icon_rain)
           "11n" -> _setWeatherIcon().postValue(R.drawable.icon_storm)
           "13n" -> _setWeatherIcon().postValue(R.drawable.icon_snow)
       }
        return "Unknown Icon"
    }

    private fun getDataFail(error: Unit) {
        _getDatFail().postValue(error.toString())
    }

     @SuppressLint("CheckResult")
     fun requestCurrentWeather(lat: Double, lon: Double, units: String, appid: String) {
        weatherRepository.getCurrentWeatherData(lat, lon, units, appid)
                .subscribe({_setCurrentWeather().postValue(it)},{ getDataFail(it.printStackTrace())})
    }

    fun translatePlaceName(placeName: String) : String {
        if (placeName == "Zaporizhzhya") {
            return "Запорожье"
        }
        return  placeName
    }

    fun translateWeatherName(weatherName: String) : String {
        return when (weatherName) {
            "mist" -> "Туман"
            "broken clouds" -> "Облачно"
            else -> weatherName
        }
    }

}