package com.weather.weathermain.activity.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.weather.weathermain.GPSTracker
import com.weather.weathermain.R
import com.weather.weathermain.activity.main.adapter.WeatherMainAdapter
import com.weather.weathermain.activity.main.mainviewmodel.MainActivityViewModel
import com.weather.weathermain.activity.settings.SettingsActivity
import com.weather.weathermain.activity.weathertoday.CurrentWeatherActivity
import com.weather.weathermain.data.ListData
import com.weather.weathermain.data.WeatherForecast
import com.weather.weathermain.utils.constants.APP_ID
import com.weather.weathermain.utils.constants.APP_PREFERENCES
import com.weather.weathermain.utils.constants.UNITS
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    companion object {
        fun launch(context: Context) {
            val launcher = Intent(context, MainActivity::class.java)
            context.startActivity(launcher)
        }
    }

    private lateinit var model: MainActivityViewModel

    private var gpsTracker: GPSTracker? = null

    private var latitude: Double = 0.toDouble()
    private var longitude: Double = 0.toDouble()

    private var weatherMainAdapter: WeatherMainAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null

    private var sharedPreferences: SharedPreferences? = null

    private var alert: AlertDialog.Builder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull<ActionBar>(supportActionBar).title = "Погода на неделю"
        }

        try {
            if (ContextCompat.checkSelfPermission(applicationContext,
                            android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        gpsTracker = GPSTracker(this@MainActivity)

        if (gpsTracker!!.canGetLocation) {
            latitude = gpsTracker!!.getLatitude()
            longitude = gpsTracker!!.getLongitude()
        } else {
            gpsTracker!!.showSettingsAlert()
        }

        model = ViewModelProviders.of(this)[MainActivityViewModel::class.java]
        model.requestForecastWeek(latitude, longitude, UNITS, APP_ID)

        model._getDataFail().observe(this, Observer<String> { data_fail ->
            Log.d("DATA_IS_FAIL ", data_fail.toString())
        })

        model._getForecastWeek().observe(this, Observer<WeatherForecast>{ data_forecast ->
            generateDataList(data_forecast!!.list)
        })

        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val switchAskExitState = sharedPreferences!!.getBoolean("switchAskWhenExit", false)

        linearLayoutManager = LinearLayoutManager(this)
        recycler_main.layoutManager = linearLayoutManager

        log_out.setOnClickListener {
            val title = resources.getString(R.string.ask_exit)
            val button1 = resources.getString(R.string.ask_exit_yes)
            val button2 = resources.getString(R.string.ask_exit_no)

            if (switchAskExitState) {
                alert = AlertDialog.Builder(this@MainActivity)
                alert!!.setTitle(title)
                alert!!.setPositiveButton(button1) { dialogInterface, i -> finish() }
                alert!!.setNegativeButton(button2) { dialogInterface, i -> alert!!.setCancelable(true) }
                alert!!.show()

            } else {
                finish()
            }
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
    }

    fun generateDataList(body: List<ListData>) {
        weatherMainAdapter = WeatherMainAdapter(body)
        recycler_main!!.adapter = weatherMainAdapter
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_weather_today -> CurrentWeatherActivity.launch(this)
            R.id.nav_weather_week -> { }
            R.id.nav_drawer_settings -> {
                SettingsActivity.launch(this)
                finish()
            }
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

}
//221