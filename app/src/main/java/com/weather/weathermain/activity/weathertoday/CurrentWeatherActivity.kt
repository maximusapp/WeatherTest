package com.weather.weathermain.activity.weathertoday

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import com.jakewharton.rxbinding2.view.RxView
import com.weather.weathermain.R
import com.weather.weathermain.WeatherApplication
import com.weather.weathermain.activity.weathertoday.viewmodel.CurrentWeatherViewModel
import com.weather.weathermain.data.LocationData
import com.weather.weathermain.data.WeatherOnTodayResponse
import com.weather.weathermain.data.repository.WeatherRemoteRepository
import com.weather.weathermain.utils.constants.*
import com.weather.weathermain.utils.extensions.*
import kotlinx.android.synthetic.main.activity_weather_on_today.*
import kotlinx.android.synthetic.main.dialog_internet_error.*
import java.util.*
import javax.inject.Inject

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

    @Inject
    lateinit var weatherRemoteRepository: WeatherRemoteRepository

    private lateinit var factory: CurrentWeatherViewModel.Factory
    private lateinit var weatherModel: CurrentWeatherViewModel
    private val date: Date = Calendar.getInstance().time

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_on_today)
        WeatherApplication.appComponent.inject(this)

        factory = CurrentWeatherViewModel.Factory(weatherRemoteRepository)
        weatherModel = ViewModelProviders.of(this, factory)[CurrentWeatherViewModel::class.java]

        setupUi()
        requestLiveData()
        setViewLiveData()
        observeLiveData()
    }

    private fun requestLiveData() {
        weatherModel.requestCurrentWeather(location()!!.latitude, location()!!.longitude, UNITS, APP_ID)
    }

    private fun setViewLiveData() {
        weatherModel._setCurrentWeather().observe(this, Observer<WeatherOnTodayResponse> { data ->
            iv_update_current_weather.setImageResource(R.drawable.ic_refresh)

            weatherModel.getWeatherIcon(data!!.weather[0].icon!!)

            main_container_two.showViewWithScaleAnim()
            main_container.showViewWithScaleAnim(400)
            container_additional.showViewWithScaleAnim(500)

            tv_degree_progress.visibility = View.GONE

            place.text = weatherModel.translatePlaceName(data.name!!)
            tv_weather_name.text = weatherModel.translateWeatherName(data.weather[0].id!!)
            tv_degree.text = data.main?.temp
            tv_humidity.text = data.main!!.humidity
            tv_pressure.text = data.main.pressure
            tv_wind_speed_value.text = data.wind?.speed

            tv_current_day.text = currentDayFormat(date.time)
            tv_current_month.text = currentMonthFormat(date.time)

            tv_sunrise_value.text = timeFormat(data.sys?.sunrise!!)
            tv_sunset_value.text = timeFormat(data.sys.sunset!!)
        })

        weatherModel._setWeatherIcon().observe(this, Observer<Int> { icon ->
                iv_progress.visibility = View.GONE
                iv_weather_today.setImageResource(icon!!)
        })

        weatherModel._getDatFail().observe(this, Observer<Boolean> {
                main_container_dialog.showViewWithScaleAnim()
                viewDim.visibility = View.VISIBLE
        })

    }

    private fun observeLiveData(){
        weatherModel._getUpdate().observe(this, Observer<Boolean> {
            iv_update_current_weather.startAnimation( AnimationUtils.loadAnimation(
                    this, R.anim.rotate))
        })

        weatherModel._setCloseBtnClicked().observe(this, Observer<Boolean> {
            main_container_dialog.visibility = View.GONE
            viewDim.visibility = View.GONE
            iv_update_current_weather.setImageResource(R.drawable.ic_should_refresh)
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

        RxView.clicks(btnCloseDialog)
                .subscribe{ weatherModel.setCloseBtnClicked(true) }

        RxView.clicks(btnUpdateInternet)
                .doOnNext { main_container_dialog.visibility = View.GONE }
                .doOnNext { viewDim.visibility = View.GONE }
                .doOnNext { weatherModel.setUpdate(true) }
                .subscribe { weatherModel.requestCurrentWeather(location()!!.latitude, location()!!.longitude, UNITS, APP_ID) }
    }

} // 178