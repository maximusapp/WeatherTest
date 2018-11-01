package com.weatherkremenchug.weatherkremenchugtest.data;

import java.util.ArrayList;

public class WeatherForecast {
    public String cod;
    public String message;
    public String cnt;
    public ArrayList<ListData> list = new ArrayList<>();

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public ArrayList<ListData> getList() {
        return list;
    }

    public void setList(ArrayList<ListData> list) {
        this.list = list;
    }
}
