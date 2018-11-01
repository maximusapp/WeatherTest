package com.weatherkremenchug.weatherkremenchugtest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.weatherkremenchug.weatherkremenchugtest.activity.SettingsActivity;
import com.weatherkremenchug.weatherkremenchugtest.activity.WeatherOnTodayActivity;
import com.weatherkremenchug.weatherkremenchugtest.adapter.WeatherMainAdapter;
import com.weatherkremenchug.weatherkremenchugtest.data.ListData;
import com.weatherkremenchug.weatherkremenchugtest.data.WeatherForecast;
import com.weatherkremenchug.weatherkremenchugtest.retrofit.CallToServer;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @SuppressWarnings("SpellCheckingInspection")
    private static final String APP_ID = "c3eebf803f44713f50e31a7c5b215a73";

    GPSTracker gpsTracker;

    double latitude;
    double longitude;

    String units = "metric";

    RecyclerView recyclerView;
    WeatherMainAdapter weatherMainAdapter;
    LinearLayoutManager linearLayoutManager;

    TextView log_out;

    SharedPreferences sharedPreferences;
    public static final String APP_PREFERENCES = "settings";

    AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Погода на неделю");
        }

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        gpsTracker = new GPSTracker(MainActivity.this);

        if (gpsTracker.canGetLocation) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
        } else {
            gpsTracker.showSettingsAlert();
        }
        
        callWeather();

        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        final boolean switch_ask_exit_state = sharedPreferences.getBoolean("switch_ask_when_exit", false);

        recyclerView = findViewById(R.id.recycler_main);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        log_out = findViewById(R.id.log_out);
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = getResources().getString(R.string.ask_exit);
                String button1 = getResources().getString(R.string.ask_exit_yes);
                String button2 = getResources().getString(R.string.ask_exit_no);

                if (switch_ask_exit_state) {
                    alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle(title);

                    alert.setPositiveButton(button1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });

                    alert.setNegativeButton(button2, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            alert.setCancelable(true);
                        }
                    });

                    alert.show();

                } else {
                    finish();
                }


            }
        });

       // Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void callWeather() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CallToServer callToServer = retrofit.create(CallToServer.class);

        Call<WeatherForecast> call = callToServer.getForecast(latitude, longitude, units, APP_ID);
        call.enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(@NonNull Call<WeatherForecast> call, @NonNull Response<WeatherForecast> response) {

                if (response.isSuccessful()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        generateDataList(Objects.requireNonNull(response.body()).getList());
                    }
                }

                Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(@NonNull Call<WeatherForecast> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "NOT_OK", Toast.LENGTH_LONG).show();
            }
        });

    }


    public void generateDataList(List<ListData> body) {
        weatherMainAdapter = new WeatherMainAdapter(this, body);
        recyclerView.setAdapter(weatherMainAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_weather_today) {
            Intent intent_weather_on_today = new Intent(MainActivity.this, WeatherOnTodayActivity.class);
            startActivity(intent_weather_on_today);
        } else if (id == R.id.nav_weather_week) {

        } else if (id == R.id.nav_drawer_settings) {
            Intent intent_settings = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent_settings);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
