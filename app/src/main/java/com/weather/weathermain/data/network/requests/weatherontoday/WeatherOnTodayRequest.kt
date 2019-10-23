package com.weather.weathermain.data.network.requests.weatherontoday

import com.google.gson.annotations.SerializedName

class WeatherOnTodayRequest (
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Double,
    @SerializedName("units")
    val units: String,
    @SerializedName("appid")
    val app_id: String
)