package com.weather.weathermain.activity.weathertoday

import android.Manifest
import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.jakewharton.rxbinding2.view.RxView

import com.weather.weathermain.GPSTracker
import com.weather.weathermain.R
import com.weather.weathermain.activity.weathertoday.viewmodel.WeatherOnTodayViewModel
import com.weather.weathermain.data.WeatherOnTodayEntity
import com.weather.weathermain.data.network.service.WeatherService
import com.weather.weathermain.data.repository.WeatherRemoteRepository
import com.weather.weathermain.data.repository.WeatherRepository
import com.weather.weathermain.utils.extensions.showToast
import kotlinx.android.synthetic.main.activity_weather_on_today.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    private var appid: String = "c3eebf803f44713f50e31a7c5b215a73"

    private val units: String = "metric"

    private lateinit var model: WeatherOnTodayViewModel

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

        model.requestCurrentWeather(latitude, longitude, units, appid)

        observeLiveData()
        setViewLiveData()
//        callWeather()
    }

    private fun setViewLiveData() {
        model._getCurrentWeather().observe(this, Observer<WeatherOnTodayEntity> { data ->
            place.text = data?.name
        })
    }

    private fun observeLiveData() {
        model._getBackPressed().observe(this, Observer<Boolean>{
            if (it == true) onBackPressed()
        })


    }

    private fun setupUi() {
        setupClickListeners()
    }

    @SuppressLint("CheckResult")
    private fun setupClickListeners() {
        RxView.clicks(btnBack)
                .subscribe{ model.onBackClicked() }
    }

//    private fun callWeather() {
//
//        val retrofit = Retrofit.Builder()
//                .baseUrl("https://api.openweathermap.org/data/2.5/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//
//        val callToServer = retrofit.create<WeatherService>(WeatherService::class.java)
//
//        val call = callToServer.getWeatherd(latitude, longitude, units, appid)
//        call.enqueue(object : Callback<WeatherOnTodayEntity> {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            override fun onResponse(call: Call<WeatherOnTodayEntity>, response: Response<WeatherOnTodayEntity>) {
//                if (response.isSuccessful) {
//                    showToast("Success")
//                    if (response.body() != null) {
//
//                        when {
//                            Objects.requireNonNull<WeatherOnTodayEntity>(response.body()).weather[0].icon == "04d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_cloudy)
//                            Objects.requireNonNull<WeatherOnTodayEntity>(response.body()).weather[0].icon == "02d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_day_cloudy)
//                            Objects.requireNonNull<WeatherOnTodayEntity>(response.body()).weather[0].icon == "01d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_day_sunny)
//                            Objects.requireNonNull<WeatherOnTodayEntity>(response.body()).weather[0].icon == "03d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_cloud)
//                            Objects.requireNonNull<WeatherOnTodayEntity>(response.body()).weather[0].icon == "09d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_rain)
//                            Objects.requireNonNull<WeatherOnTodayEntity>(response.body()).weather[0].icon == "10d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_day_rain)
//                            Objects.requireNonNull<WeatherOnTodayEntity>(response.body()).weather[0].icon == "11d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_thunderstorm)
//                            Objects.requireNonNull<WeatherOnTodayEntity>(response.body()).weather[0].icon == "13d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_snow)
//                            Objects.requireNonNull<WeatherOnTodayEntity>(response.body()).weather[0].icon == "50d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_day_haze)
//                            Objects.requireNonNull<WeatherOnTodayEntity>(response.body()).weather[0].icon == "01n" -> image_weather_today?.setImageResource(R.drawable.ic_wi_night_clear)
//                            Objects.requireNonNull<WeatherOnTodayEntity>(response.body()).weather[0].icon == "02n" -> image_weather_today?.setImageResource(R.drawable.ic_wi_night_alt_partly_cloudy)
//                            Objects.requireNonNull<WeatherOnTodayEntity>(response.body()).weather[0].icon == "03n" -> image_weather_today?.setImageResource(R.drawable.ic_wi_night_alt_cloudy)
//                            Objects.requireNonNull<WeatherOnTodayEntity>(response.body()).weather[0].icon == "04n" -> image_weather_today?.setImageResource(R.drawable.ic_wi_cloud)
//                            Objects.requireNonNull<WeatherOnTodayEntity>(response.body()).weather[0].icon == "09n" -> image_weather_today?.setImageResource(R.drawable.ic_wi_rain)
//                            Objects.requireNonNull<WeatherOnTodayEntity>(response.body()).weather[0].icon == "10n" -> image_weather_today?.setImageResource(R.drawable.ic_wi_night_alt_rain)
//                            Objects.requireNonNull<WeatherOnTodayEntity>(response.body()).weather[0].icon == "11n" -> image_weather_today?.setImageResource(R.drawable.ic_wi_thunderstorm)
//                            Objects.requireNonNull<WeatherOnTodayEntity>(response.body()).weather[0].icon == "13n" -> image_weather_today?.setImageResource(R.drawable.ic_wi_snow)
//                            Objects.requireNonNull<WeatherOnTodayEntity>(response.body()).weather[0].icon == "50d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_night_fog)
//                        }
//
//                        place.text = response.body()!!.name
//
//
//                    }
//
//                }
//            }
//
//            override fun onFailure(call: Call<WeatherOnTodayEntity>, t: Throwable) {
//                Log.d("Message", t.message.toString())
//
//            }
//        })
//
//    }

}
// 178