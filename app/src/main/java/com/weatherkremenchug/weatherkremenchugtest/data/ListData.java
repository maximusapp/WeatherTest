package com.weatherkremenchug.weatherkremenchugtest.data;

import java.util.ArrayList;

public class ListData {

    public String dt;
    public MainWeatherData main;
    public ArrayList<WeatherData> weather = new ArrayList<>();
    public CloudsData clouds;
    public WindData wind;
    public RainData rain;
    public SysData sys;
    public String dt_txt;

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public MainWeatherData getMain() {
        return main;
    }

    public void setMain(MainWeatherData main) {
        this.main = main;
    }

    public ArrayList<WeatherData> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<WeatherData> weather) {
        this.weather = weather;
    }

    public CloudsData getClouds() {
        return clouds;
    }

    public void setClouds(CloudsData clouds) {
        this.clouds = clouds;
    }

    public WindData getWind() {
        return wind;
    }

    public void setWind(WindData wind) {
        this.wind = wind;
    }

    public RainData getRain() {
        return rain;
    }

    public void setRain(RainData rain) {
        this.rain = rain;
    }

    public SysData getSys() {
        return sys;
    }

    public void setSys(SysData sys) {
        this.sys = sys;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }
}
