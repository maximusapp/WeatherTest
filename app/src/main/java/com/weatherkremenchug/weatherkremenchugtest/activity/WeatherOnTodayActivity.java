package com.weatherkremenchug.weatherkremenchugtest.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.weatherkremenchug.weatherkremenchugtest.GPSTracker;
import com.weatherkremenchug.weatherkremenchugtest.R;
import com.weatherkremenchug.weatherkremenchugtest.data.WeatherOnToday;
import com.weatherkremenchug.weatherkremenchugtest.retrofit.CallToServer;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherOnTodayActivity extends AppCompatActivity {

    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

    private static final String APP_ID = "c3eebf803f44713f50e31a7c5b215a73";
    String units = "metric";

    GPSTracker gpsTracker;

    double latitude;
    double longitude;

    ImageView image_weather_today;
    TextView textView_high_degree;
    TextView textView_low_degree;
    TextView textView_pressure;
    TextView textView_vlaznost;
    TextView textView_wind;
    TextView place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_on_today);
        Toolbar toolbar = findViewById(R.id.toolbar_weather_today);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Погода на сегодня");
        }

        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(this.getResources().getColor(R.color.colorGray));
        }

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        gpsTracker = new GPSTracker(WeatherOnTodayActivity.this);

        if (gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
        } else {
            gpsTracker.showSettingsAlert();
        }

        image_weather_today = findViewById(R.id.image_weather_today);
        textView_high_degree = findViewById(R.id.degrees_high);
        textView_low_degree = findViewById(R.id.degrees_low);
        textView_pressure = findViewById(R.id.pressure_number);
        textView_vlaznost = findViewById(R.id.vlaga_number);
        textView_wind = findViewById(R.id.wind_number);
        place = findViewById(R.id.place);

        callWeather();

    }

    private void callWeather() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CallToServer callToServer = retrofit.create(CallToServer.class);

        Call<WeatherOnToday> call = callToServer.getWeather(latitude, longitude, units, APP_ID);
        call.enqueue(new Callback<WeatherOnToday>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<WeatherOnToday> call, @NonNull Response<WeatherOnToday> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        textView_high_degree.setText(Objects.requireNonNull(response.body()).main.getTemp_max());
                        textView_low_degree.setText(Objects.requireNonNull(response.body()).main.getTemp_min());
                        textView_pressure.setText(Objects.requireNonNull(response.body()).main.getPressure());
                        textView_vlaznost.setText(Objects.requireNonNull(response.body()).main.getHumidity());
                        textView_wind.setText(Objects.requireNonNull(response.body()).wind.getSpeed());
                        place.setText(Objects.requireNonNull(response.body()).getName());

                        if (Objects.requireNonNull(response.body()).weather.get(0).getIcon().equals("04d")) {
                            image_weather_today.setImageResource(R.drawable.ic_wi_cloudy);
                        } else if (Objects.requireNonNull(response.body()).weather.get(0).getIcon().equals("02d")) {
                            image_weather_today.setImageResource(R.drawable.ic_wi_day_cloudy);
                        } else if (Objects.requireNonNull(response.body()).weather.get(0).getIcon().equals("01d")) {
                            image_weather_today.setImageResource(R.drawable.ic_wi_day_sunny);
                        } else if (Objects.requireNonNull(response.body()).weather.get(0).getIcon().equals("03d")) {
                            image_weather_today.setImageResource(R.drawable.ic_wi_cloud);
                        } else if (Objects.requireNonNull(response.body()).weather.get(0).getIcon().equals("09d")) {
                            image_weather_today.setImageResource(R.drawable.ic_wi_rain);
                        } else if (Objects.requireNonNull(response.body()).weather.get(0).getIcon().equals("10d")) {
                            image_weather_today.setImageResource(R.drawable.ic_wi_day_rain);
                        } else if (Objects.requireNonNull(response.body()).weather.get(0).getIcon().equals("11d")) {
                            image_weather_today.setImageResource(R.drawable.ic_wi_thunderstorm);
                        } else if (Objects.requireNonNull(response.body()).weather.get(0).getIcon().equals("13d")) {
                            image_weather_today.setImageResource(R.drawable.ic_wi_snow);
                        } else if (Objects.requireNonNull(response.body()).weather.get(0).getIcon().equals("50d")) {
                            image_weather_today.setImageResource(R.drawable.ic_wi_day_haze);
                        } else if (Objects.requireNonNull(response.body()).weather.get(0).getIcon().equals("01n")) {
                            image_weather_today.setImageResource(R.drawable.ic_wi_night_clear);
                        } else if (Objects.requireNonNull(response.body()).weather.get(0).getIcon().equals("02n")) {
                            image_weather_today.setImageResource(R.drawable.ic_wi_night_alt_partly_cloudy);
                        } else if (Objects.requireNonNull(response.body()).weather.get(0).getIcon().equals("03n")) {
                            image_weather_today.setImageResource(R.drawable.ic_wi_night_alt_cloudy);
                        } else if (Objects.requireNonNull(response.body()).weather.get(0).getIcon().equals("04n")) {
                            image_weather_today.setImageResource(R.drawable.ic_wi_cloud);
                        } else if (Objects.requireNonNull(response.body()).weather.get(0).getIcon().equals("09n")) {
                            image_weather_today.setImageResource(R.drawable.ic_wi_rain);
                        } else if (Objects.requireNonNull(response.body()).weather.get(0).getIcon().equals("10n")) {
                            image_weather_today.setImageResource(R.drawable.ic_wi_night_alt_rain);
                        } else if (Objects.requireNonNull(response.body()).weather.get(0).getIcon().equals("11n")) {
                            image_weather_today.setImageResource(R.drawable.ic_wi_thunderstorm);
                        } else if (Objects.requireNonNull(response.body()).weather.get(0).getIcon().equals("13n")) {
                            image_weather_today.setImageResource(R.drawable.ic_wi_snow);
                        } else if (Objects.requireNonNull(response.body()).weather.get(0).getIcon().equals("50d")) {
                            image_weather_today.setImageResource(R.drawable.ic_wi_night_fog);
                        }

                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherOnToday> call, @NonNull Throwable t) {
                Toast.makeText(WeatherOnTodayActivity.this, "Bad request", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
