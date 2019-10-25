package com.weather.weathermain.activity.weathertoday

import android.Manifest
import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.weather.weathermain.GPSTracker
import com.weather.weathermain.R
import com.weather.weathermain.activity.weathertoday.viewmodel.CurrentWeatherViewModel
import com.weather.weathermain.data.WeatherOnTodayResponse
import com.weather.weathermain.utils.constants.APP_ID
import com.weather.weathermain.utils.constants.UNITS
import kotlinx.android.synthetic.main.activity_weather_on_today.*
import java.text.SimpleDateFormat
import java.util.*

class CurrentWeatherActivity : AppCompatActivity() {
    companion object {
        fun launch(context: Context) {
            val launcher = Intent(context, CurrentWeatherActivity::class.java)
            context.startActivity(launcher)
        }
    }

    private var gpsTracker: GPSTracker? = null

    private var latitude: Double = 0.toDouble()
    private var longitude: Double = 0.toDouble()

    private lateinit var model: CurrentWeatherViewModel

   private val calendar: Calendar = Calendar.getInstance()
    private val date: Date = calendar.time

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_on_today)

        model = ViewModelProviders.of(this)[CurrentWeatherViewModel::class.java]

        setupUi()

        try {
            if (ContextCompat.checkSelfPermission(applicationContext,
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        gpsTracker = GPSTracker(this@CurrentWeatherActivity)

        if (gpsTracker!!.canGetLocation()) {
            latitude = gpsTracker!!.latitude
            longitude = gpsTracker!!.longitude

        } else {
            gpsTracker!!.showSettingsAlert()
        }

        observeLiveData()
        setViewLiveData()
    }

    private fun observeLiveData() {
        model.requestCurrentWeather(latitude, longitude, UNITS, APP_ID)
    }

    private fun setViewLiveData() {
        model._setCurrentWeather().observe(this, Observer<WeatherOnTodayResponse> { data ->
            place.text = data!!.name

            model.getWeatherIcon(data.weather[0].icon!!)

            tv_weather_name.text = data.weather[0].description
            tv_degree.text = data.main?.temp
            tv_humidity.text = data.main?.humidity
            tv_pressure.text = data.main?.pressure
            tv_wind_speed_value.text = data.wind?.speed

            tv_current_day.text = SimpleDateFormat("EEEE", Locale.getDefault()).format(date.time)
            tv_current_month.text = SimpleDateFormat("MMMM, d", Locale.getDefault()).format(date.time)

            tv_sunrise_value.text = SimpleDateFormat("hh:mm", Locale.getDefault()).format(data.sys?.sunrise!! * 1000L)
            tv_sunset_value.text = SimpleDateFormat("hh:mm", Locale.getDefault()).format(data.sys.sunset!! * 1000L)

        })

        model._setWeatherIcon().observe(this, Observer<Int> { icon ->
            iv_weather_today.setImageResource(icon!!)
        })

        model._getDatFail().observe(this, Observer<String> { data_fail ->
            Log.d("DATA_IS_FAIL ", data_fail.toString())
        })

    }

    private fun setupUi() {
        setupClickListeners()
    }

    @SuppressLint("CheckResult")
    private fun setupClickListeners() {
//        RxView.clicks(btnBack)
//                .subscribe { model.getWeatherIcon() }
    }

} // 178