package com.weatherkremenchug.weathermain.retrofit

import com.weatherkremenchug.weathermain.data.WeatherForecast
import com.weatherkremenchug.weathermain.data.WeatherOnToday

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CallToServer {

    @GET("forecast")
    fun getForecast(@Query("lat") lat: Double?,
                    @Query("lon") lon: Double?,
                    @Query("units") units: String,
                    @Query("appid") appid: String): Call<WeatherForecast>

    @GET("weather")
    fun getWeather(@Query("lat") lat: Double?,
                   @Query("lon") lon: Double?,
                   @Query("units") units: String,
                   @Query("appid") appid: String): Call<WeatherOnToday>

}