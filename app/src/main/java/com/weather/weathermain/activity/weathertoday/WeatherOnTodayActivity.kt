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
import com.jakewharton.rxbinding2.view.RxView
import com.weather.weathermain.GPSTracker
import com.weather.weathermain.R
import com.weather.weathermain.activity.main.MainActivity
import com.weather.weathermain.activity.weathertoday.viewmodel.WeatherOnTodayViewModel
import com.weather.weathermain.data.WeatherOnTodayResponse
import com.weather.weathermain.utils.constants.APP_ID
import com.weather.weathermain.utils.constants.UNITS
import kotlinx.android.synthetic.main.activity_weather_on_today.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class WeatherOnTodayActivity : AppCompatActivity() {
    companion object {
        fun launch(context: Context) {
            val launcher = Intent(context, WeatherOnTodayActivity::class.java)
            context.startActivity(launcher)
        }
    }

    private var gpsTracker: GPSTracker? = null

    private var latitude: Double = 0.toDouble()
    private var longitude: Double = 0.toDouble()

    private lateinit var model: WeatherOnTodayViewModel

   private val calendar: Calendar = Calendar.getInstance()
    private val date: Date = calendar.time

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_on_today)

        model = ViewModelProviders.of(this)[WeatherOnTodayViewModel::class.java]

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

        gpsTracker = GPSTracker(this@WeatherOnTodayActivity)

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
        model._getCurrentWeather().observe(this, Observer<WeatherOnTodayResponse> { data ->
            place.text = data!!.name
            if (data.weather[0].icon == "50d") iv_weather_today.setImageResource(R.drawable.icon_haze)
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

        model._getBackPressed().observe(this, Observer<Boolean> {
            if (it == true) {
                MainActivity.launch(this)
                finish()
            }
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
//                .subscribe { model.onBackClicked() }
    }

//   when {
//  Objects.requireNonNull<WeatherOnTodayResponse>(response.body()).weather[0].icon == "04d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_cloudy)
//  Objects.requireNonNull<WeatherOnTodayResponse>(response.body()).weather[0].icon == "02d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_day_cloudy)
//  Objects.requireNonNull<WeatherOnTodayResponse>(response.body()).weather[0].icon == "01d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_day_sunny)
//  Objects.requireNonNull<WeatherOnTodayResponse>(response.body()).weather[0].icon == "03d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_cloud)
//  Objects.requireNonNull<WeatherOnTodayResponse>(response.body()).weather[0].icon == "09d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_rain)
//  Objects.requireNonNull<WeatherOnTodayResponse>(response.body()).weather[0].icon == "10d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_day_rain)
//  Objects.requireNonNull<WeatherOnTodayResponse>(response.body()).weather[0].icon == "11d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_thunderstorm)
//  Objects.requireNonNull<WeatherOnTodayResponse>(response.body()).weather[0].icon == "13d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_snow)
//  Objects.requireNonNull<WeatherOnTodayResponse>(response.body()).weather[0].icon == "50d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_day_haze)
//  Objects.requireNonNull<WeatherOnTodayResponse>(response.body()).weather[0].icon == "01n" -> image_weather_today?.setImageResource(R.drawable.ic_wi_night_clear)
//  Objects.requireNonNull<WeatherOnTodayResponse>(response.body()).weather[0].icon == "02n" -> image_weather_today?.setImageResource(R.drawable.ic_wi_night_alt_partly_cloudy)
//  Objects.requireNonNull<WeatherOnTodayResponse>(response.body()).weather[0].icon == "03n" -> image_weather_today?.setImageResource(R.drawable.ic_wi_night_alt_cloudy)
//  Objects.requireNonNull<WeatherOnTodayResponse>(response.body()).weather[0].icon == "04n" -> image_weather_today?.setImageResource(R.drawable.ic_wi_cloud)
//  Objects.requireNonNull<WeatherOnTodayResponse>(response.body()).weather[0].icon == "09n" -> image_weather_today?.setImageResource(R.drawable.ic_wi_rain)
//  Objects.requireNonNull<WeatherOnTodayResponse>(response.body()).weather[0].icon == "10n" -> image_weather_today?.setImageResource(R.drawable.ic_wi_night_alt_rain)
//  Objects.requireNonNull<WeatherOnTodayResponse>(response.body()).weather[0].icon == "11n" -> image_weather_today?.setImageResource(R.drawable.ic_wi_thunderstorm)
//  Objects.requireNonNull<WeatherOnTodayResponse>(response.body()).weather[0].icon == "13n" -> image_weather_today?.setImageResource(R.drawable.ic_wi_snow)
//  Objects.requireNonNull<WeatherOnTodayResponse>(response.body()).weather[0].icon == "50d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_night_fog)
} // 178