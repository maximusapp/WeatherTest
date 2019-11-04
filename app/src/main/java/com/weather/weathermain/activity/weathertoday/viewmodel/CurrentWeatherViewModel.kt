package com.weather.weathermain.activity.weathertoday.viewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.weather.weathermain.R
import com.weather.weathermain.data.WeatherOnTodayResponse
import com.weather.weathermain.data.repository.WeatherRemoteRepository
import io.reactivex.disposables.CompositeDisposable

class CurrentWeatherViewModel(private val weatherRepository: WeatherRemoteRepository) : ViewModel() {

    private var icon: MutableLiveData<Int> = MutableLiveData()
    private var currentWeather: MutableLiveData<WeatherOnTodayResponse> = MutableLiveData()

    private val data: MutableLiveData<String> = MutableLiveData()
    private val update: MutableLiveData<Boolean> = MutableLiveData()

    private var disposable: CompositeDisposable = CompositeDisposable()

    fun _setCurrentWeather(): MutableLiveData<WeatherOnTodayResponse>  { return currentWeather }
    fun _setWeatherIcon(): MutableLiveData<Int> { return icon }
    fun _getDatFail(): MutableLiveData<String> { return data }
    fun _getUpdate(): MutableLiveData<Boolean> { return update }

    fun getWeatherIcon(icon: String): String {
       when (icon) {
           "01d" -> _setWeatherIcon().postValue(R.drawable.ic_sunny)
           "02d" -> _setWeatherIcon().postValue(R.drawable.ic_cloudy_day)
           "03d" -> _setWeatherIcon().postValue(R.drawable.ic_clouds)
           "04d" -> _setWeatherIcon().postValue(R.drawable.ic_clouds)
           "50d" -> _setWeatherIcon().postValue(R.drawable.icon_haze_day)
           "09d" -> _setWeatherIcon().postValue(R.drawable.ic_shower_rain)
           "10d" -> _setWeatherIcon().postValue(R.drawable.ic_rain)
           "11d" -> _setWeatherIcon().postValue(R.drawable.ic_storm)
           "13d" -> _setWeatherIcon().postValue(R.drawable.ic_snow)

           "01n" -> _setWeatherIcon().postValue(R.drawable.ic_moon)
           "02n" -> _setWeatherIcon().postValue(R.drawable.icon_few_clouds_night)
           "03n" -> _setWeatherIcon().postValue(R.drawable.ic_clouds)
           "04n" -> _setWeatherIcon().postValue(R.drawable.ic_clouds)
           "50n" -> _setWeatherIcon().postValue(R.drawable.icon_haze_night)
           "09n" -> _setWeatherIcon().postValue(R.drawable.ic_shower_rain)
           "10n" -> _setWeatherIcon().postValue(R.drawable.ic_rain)
           "11n" -> _setWeatherIcon().postValue(R.drawable.ic_storm)
           "13n" -> _setWeatherIcon().postValue(R.drawable.ic_snow)
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

    fun setUpdate(update: Boolean){
        _getUpdate().postValue(update)
    }

    fun translatePlaceName(placeName: String) : String {
        if (placeName == "Zaporizhzhya") {
            return "Запорожье"
        }
        return  placeName
    }

    fun translateWeatherName(weatherId: Int) : String {
        return when (weatherId) {
            500 -> "Легкий дождик"
            501 -> "Умеренный дождь"
            502 -> "Интенсивняй дождь"
            503 -> "Сильный интенсивный дождь"
            504 -> "Сильный дождь"
            511 -> "Дождь со снегом"
            520 -> "Легкий интенсивный рассеяный дождь"
            521 -> "Легкий дождь"
            522 -> "Сильный дождь"
            531 -> "Порывистый дождь"

            701 -> "Туман"
            711 -> "Туман с дымом"
            721 -> "Легкий туман"
            731 -> "Пыльно"
            741 -> "Туман"
            761 -> "Пыль"

            800 -> "Ясно"
            801 -> "Слегка Облачно: 11-25%"
            802 -> "Рассеяные облака: 25-50%"
            803 -> "Облачно: 51-84%"
            804 -> "Пасмурные облака: 85-100%"
            else -> "Неопределенная погода"
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
            private val weatherRemoteRepo: WeatherRemoteRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CurrentWeatherViewModel::class.java)) {
                return CurrentWeatherViewModel(weatherRemoteRepo) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}