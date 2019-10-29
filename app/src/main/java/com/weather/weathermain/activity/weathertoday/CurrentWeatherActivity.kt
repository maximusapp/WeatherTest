package com.weather.weathermain.activity.weathertoday

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import com.jakewharton.rxbinding2.view.RxView
import com.weather.weathermain.R
import com.weather.weathermain.activity.weathertoday.viewmodel.CurrentWeatherViewModel
import com.weather.weathermain.data.LocationData
import com.weather.weathermain.data.WeatherOnTodayResponse
import com.weather.weathermain.utils.constants.*
import com.weather.weathermain.utils.extensions.*
import kotlinx.android.synthetic.main.activity_weather_on_today.*
import java.util.*

class CurrentWeatherActivity : AppCompatActivity() {
    companion object {
        private const val LOCATION = "location"
        @JvmStatic
        fun launch(context: Context, locationData: LocationData?) {
            val launcher = Intent(context, CurrentWeatherActivity::class.java)
                    .putExtra(LOCATION, locationData as Parcelable)
            context.startActivity(launcher)
        }
    }

    private fun location() = intent.getParcelableExtra<LocationData>(LOCATION)

    private lateinit var weatherModel: CurrentWeatherViewModel
    private val calendar: Calendar = Calendar.getInstance()
    private val date: Date = calendar.time

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_on_today)

        weatherModel = ViewModelProviders.of(this)[CurrentWeatherViewModel::class.java]

        setupUi()
        requestLiveData()
        setViewLiveData()
        observeLiveData()
    }

    private fun requestLiveData() {
        weatherModel.requestCurrentWeather(location()!!.latitude, location()!!.longitude, UNITS, APP_ID)
    }

    private fun setViewLiveData() {
        weatherModel._setCurrentWeather().observe(this, Observer<WeatherOnTodayResponse> {
            weatherModel.getWeatherIcon(it!!.weather[0].icon!!)

            tv_weather_name.visibility = View.VISIBLE
            main_container.visibility = View.VISIBLE
            tv_degree_progress.visibility = View.GONE

            place.text = weatherModel.translatePlaceName(it.name!!)
            tv_weather_name.text = weatherModel.translateWeatherName(it.weather[0].id!!)
            tv_degree.text = it.main?.temp
            tv_humidity.text = it.main!!.humidity
            tv_pressure.text = it.main.pressure
            tv_wind_speed_value.text = it.wind?.speed

            tv_current_day.text = currentDayFormat(date.time)
            tv_current_month.text = currentMonthFormat(date.time)

            tv_sunrise_value.text = timeFormat(it.sys?.sunrise!!)
            tv_sunset_value.text = timeFormat(it.sys.sunset!!)
        })

        weatherModel._setWeatherIcon().observe(this, Observer<Int> { icon ->
                iv_progress.visibility = View.GONE
                iv_weather_today.visibility = View.VISIBLE
                iv_weather_today.setImageResource(icon!!)
        })

        weatherModel._getDatFail().observe(this, Observer<String> { data_fail ->
            Log.d("DATA_IS_FAIL ", data_fail.toString())
        })

    }

    private fun observeLiveData(){
        weatherModel._getUpdate().observe(this, Observer<Boolean> {
            iv_update_current_weather.startAnimation( AnimationUtils.loadAnimation(
                    this, R.anim.rotate))
        })
    }

    private fun setupUi() {
        setupClickListeners()
    }

    @SuppressLint("CheckResult")
    private fun setupClickListeners() {
        RxView.clicks(iv_update_current_weather)
                .doOnNext { weatherModel.requestCurrentWeather(location()!!.latitude, location()!!.longitude, UNITS, APP_ID) }
                .subscribe { weatherModel.setUpdate(true) }
    }

} // 178