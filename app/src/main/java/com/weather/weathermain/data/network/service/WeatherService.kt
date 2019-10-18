package com.weather.weathermain.data.network.service

import com.weather.weathermain.BuildConfig
import com.weather.weathermain.data.WeatherForecast
import com.weather.weathermain.data.WeatherOnTodayEntity
import io.reactivex.Single

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    companion object {
        fun get(): WeatherService = RetrofitService.createService(BuildConfig.ENDPOINT)
    }

    @GET("forecast")
    fun getForecast(@Query("lat") lat: Double?,
                    @Query("lon") lon: Double?,
                    @Query("units") units: String,
                    @Query("appid") appid: String): Call<WeatherForecast>

    // Current weather data
    @GET("weather")
    fun getWeatherd(@Query("lat") lat: Double,
                   @Query("lon") lon: Double,
                   @Query("units") units: String,
                   @Query("appid") appid: String): Call<WeatherOnTodayEntity>

    @GET("weather")
    fun getWeather(@Query("lat") lat: Double?,
                   @Query("lon") lon: Double?,
                   @Query("units") units: String,
                   @Query("appid") appid: String): Single<WeatherOnTodayEntity>
}