package com.weather.weathermain.data.enums

import android.support.annotation.StringRes
import com.weather.weathermain.R


enum class WeatherNameEng( @StringRes val nameWeather: Int) {
    MIST(R.string.weather_mist),
    BROKEN_CLOUDS(R.string.weather_broken_clouds)
}

enum class WeatherNameRus( @StringRes val nameWeather: Int) {
    MIST_RU(R.string.weather_mist_ru),
    BROKEN_CLOUDS_RU(R.string.weather_broken_clouds_ru)
}