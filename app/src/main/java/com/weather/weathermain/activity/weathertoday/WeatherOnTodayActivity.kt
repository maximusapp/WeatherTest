package com.weather.weathermain.activity.weathertoday

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.ImageView
import android.widget.TextView

import com.weather.weathermain.GPSTracker
import com.weather.weathermain.R
import com.weather.weathermain.utils.constants.APP_ID
import com.weather.weathermain.data.WeatherOnToday
import com.weather.weathermain.utils.extensions.showToast
import com.weather.weathermain.data.network.service.WeatherService
import com.weather.weathermain.domain.interactor.IWeatherInteractor
import com.weather.weathermain.domain.interactor.WeatherInteractor

import java.util.Objects

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherOnTodayActivity : AppCompatActivity() {
    companion object {
        fun launch(context: Context) {
            val launcher = Intent(context, WeatherOnTodayActivity::class.java)
            context.startActivity(launcher)
        }
    }

    private val interactor: WeatherInteractor by lazy { IWeatherInteractor.getInstance() }

    private var units = "metric"

    private var gpsTracker: GPSTracker? = null

    private var latitude: Double = 0.toDouble()
    private var longitude: Double = 0.toDouble()

    private var image_weather_today: ImageView? = null
    private var textView_high_degree: TextView? = null
    private var textView_low_degree: TextView? = null
    private var textView_pressure: TextView? = null
    private var textView_vlaznost: TextView? = null
    private var textView_wind: TextView? = null
    private var place: TextView? = null

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_on_today)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_weather_today)
        setSupportActionBar(toolbar)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull<ActionBar>(supportActionBar).setDisplayHomeAsUpEnabled(true)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull<ActionBar>(supportActionBar).title = "Погода на сегодня"
        }

        val window = this.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = this.resources.getColor(R.color.colorGray)
        }

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

        image_weather_today = findViewById(R.id.image_weather_today)
        textView_high_degree = findViewById(R.id.degrees_high)
        textView_low_degree = findViewById(R.id.degrees_low)
        textView_pressure = findViewById(R.id.pressure_number)
        textView_vlaznost = findViewById(R.id.vlaga_number)
        textView_wind = findViewById(R.id.wind_number)
        place = findViewById(R.id.place)

        callWeather()

    }

    private fun callWeather() {

        interactor.getCurrentWeatherData(latitude, longitude, units, APP_ID)


//        val retrofit = Retrofit.Builder()
//                .baseUrl("http://api.openweathermap.org/data/2.5/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()

        //val callToServer = retrofit.create<WeatherService>(WeatherService::class.java)

        //val call = callToServer.getWeather(latitude, longitude, units, APP_ID)
//        call.enqueue(object : Callback<WeatherOnToday> {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            override fun onResponse(call: Call<WeatherOnToday>, response: Response<WeatherOnToday>) {
//                if (response.isSuccessful) {
//                    if (response.body() != null) {
//                        textView_high_degree?.text = Objects.requireNonNull<WeatherOnToday>(response.body()).main?.temp_max
//                        textView_low_degree?.text = Objects.requireNonNull<WeatherOnToday>(response.body()).main?.temp_min
//                        textView_pressure?.text = Objects.requireNonNull<WeatherOnToday>(response.body()).main?.pressure
//                        textView_vlaznost?.text = Objects.requireNonNull<WeatherOnToday>(response.body()).main?.humidity
//                        textView_wind?.text = Objects.requireNonNull<WeatherOnToday>(response.body()).wind?.speed
//                        place?.text = Objects.requireNonNull<WeatherOnToday>(response.body()).name
//
//                        when {
//                            Objects.requireNonNull<WeatherOnToday>(response.body()).weather[0].icon == "04d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_cloudy)
//                            Objects.requireNonNull<WeatherOnToday>(response.body()).weather[0].icon == "02d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_day_cloudy)
//                            Objects.requireNonNull<WeatherOnToday>(response.body()).weather[0].icon == "01d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_day_sunny)
//                            Objects.requireNonNull<WeatherOnToday>(response.body()).weather[0].icon == "03d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_cloud)
//                            Objects.requireNonNull<WeatherOnToday>(response.body()).weather[0].icon == "09d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_rain)
//                            Objects.requireNonNull<WeatherOnToday>(response.body()).weather[0].icon == "10d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_day_rain)
//                            Objects.requireNonNull<WeatherOnToday>(response.body()).weather[0].icon == "11d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_thunderstorm)
//                            Objects.requireNonNull<WeatherOnToday>(response.body()).weather[0].icon == "13d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_snow)
//                            Objects.requireNonNull<WeatherOnToday>(response.body()).weather[0].icon == "50d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_day_haze)
//                            Objects.requireNonNull<WeatherOnToday>(response.body()).weather[0].icon == "01n" -> image_weather_today?.setImageResource(R.drawable.ic_wi_night_clear)
//                            Objects.requireNonNull<WeatherOnToday>(response.body()).weather[0].icon == "02n" -> image_weather_today?.setImageResource(R.drawable.ic_wi_night_alt_partly_cloudy)
//                            Objects.requireNonNull<WeatherOnToday>(response.body()).weather[0].icon == "03n" -> image_weather_today?.setImageResource(R.drawable.ic_wi_night_alt_cloudy)
//                            Objects.requireNonNull<WeatherOnToday>(response.body()).weather[0].icon == "04n" -> image_weather_today?.setImageResource(R.drawable.ic_wi_cloud)
//                            Objects.requireNonNull<WeatherOnToday>(response.body()).weather[0].icon == "09n" -> image_weather_today?.setImageResource(R.drawable.ic_wi_rain)
//                            Objects.requireNonNull<WeatherOnToday>(response.body()).weather[0].icon == "10n" -> image_weather_today?.setImageResource(R.drawable.ic_wi_night_alt_rain)
//                            Objects.requireNonNull<WeatherOnToday>(response.body()).weather[0].icon == "11n" -> image_weather_today?.setImageResource(R.drawable.ic_wi_thunderstorm)
//                            Objects.requireNonNull<WeatherOnToday>(response.body()).weather[0].icon == "13n" -> image_weather_today?.setImageResource(R.drawable.ic_wi_snow)
//                            Objects.requireNonNull<WeatherOnToday>(response.body()).weather[0].icon == "50d" -> image_weather_today?.setImageResource(R.drawable.ic_wi_night_fog)
//                        }
//
//                    }
//
//                }
//            }
//
//            override fun onFailure(call: Call<WeatherOnToday>, t: Throwable) {
//                showToast("Bad request")
//            }
//        })

    }
}
// 178