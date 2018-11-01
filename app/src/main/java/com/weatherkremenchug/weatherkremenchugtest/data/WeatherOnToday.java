package com.weatherkremenchug.weatherkremenchugtest.data;

import java.util.ArrayList;

public class WeatherOnToday {

    public CoordData coord;
    public ArrayList<WeatherData> weather = new ArrayList<>();
    public String base;
    public MainWeatherData main;
    public CloudsData clouds;
    public String dt;
    public WindData wind;
    public SysData sys;

    public String id;
    public String name;
    public String cod;

    public CoordData getCoord() {
        return coord;
    }

    public void setCoord(CoordData coord) {
        this.coord = coord;
    }

    public ArrayList<WeatherData> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<WeatherData> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public MainWeatherData getMain() {
        return main;
    }

    public void setMain(MainWeatherData main) {
        this.main = main;
    }

    public CloudsData getClouds() {
        return clouds;
    }

    public void setClouds(CloudsData clouds) {
        this.clouds = clouds;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public WindData getWind() {
        return wind;
    }

    public void setWind(WindData wind) {
        this.wind = wind;
    }

    public SysData getSys() {
        return sys;
    }

    public void setSys(SysData sys) {
        this.sys = sys;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }
}