package com.weatherkremenchug.weatherkremenchugtest.retrofit;

import com.weatherkremenchug.weatherkremenchugtest.data.WeatherForecast;
import com.weatherkremenchug.weatherkremenchugtest.data.WeatherOnToday;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CallToServer {

    @GET("forecast")
    Call<WeatherForecast> getForecast(@Query("lat") Double lat,
                                      @Query("lon") Double lon,
                                      @Query("units") String units,
                                      @Query("appid") String appid);

    @GET("weather")
    Call<WeatherOnToday> getWeather(@Query("lat") Double lat,
                                    @Query("lon") Double lon,
                                    @Query("units") String units,
                                    @Query("appid") String appid);

}