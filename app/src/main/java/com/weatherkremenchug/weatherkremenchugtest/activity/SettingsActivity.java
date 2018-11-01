package com.weatherkremenchug.weatherkremenchugtest.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Switch;
import android.widget.TextView;

import com.weatherkremenchug.weatherkremenchugtest.MainActivity;
import com.weatherkremenchug.weatherkremenchugtest.R;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    public boolean onSupportNavigateUp() {
        onBackPressed();
        Intent intent_main = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent_main);
        return true;
    }

    public static final String APP_PREFERENCES = "settings";
    SharedPreferences sharedPreferences;

    Switch switch_wi_fi;
    Switch switch_update_start;
    Switch switch_ask_when_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(getSupportActionBar()).setTitle("Настройки");
        }

        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(this.getResources().getColor(R.color.colorGray));
        }

        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        switch_wi_fi = findViewById(R.id.switch_wi_fi);
        switch_update_start = findViewById(R.id.switch_update_start);
        switch_ask_when_exit = findViewById(R.id.switch_ask_when_exit);

        switch_wi_fi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor_wi_fi = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).edit();
                editor_wi_fi.putBoolean("switch_wi_fi", switch_wi_fi.isChecked());
                editor_wi_fi.apply();
            }
        });

        switch_update_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor_update_start = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).edit();
                editor_update_start.putBoolean("switch_update_start", switch_update_start.isChecked());
                editor_update_start.apply();
            }
        });

        switch_ask_when_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor_ask_exit = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).edit();
                editor_ask_exit.putBoolean("switch_ask_when_exit", switch_ask_when_exit.isChecked());
                editor_ask_exit.apply();
            }
        });

        boolean switch_wi_fi_state = sharedPreferences.getBoolean("switch_wi_fi", false);
        boolean switch_update_on_start_state = sharedPreferences.getBoolean("switch_update_start", false);
        boolean switch_ask_exit_state = sharedPreferences.getBoolean("switch_ask_when_exit", false);

        // Handle wi_fi switch.
        if (switch_wi_fi_state) {
            switch_wi_fi.setChecked(true);
        } else {
            switch_wi_fi.setChecked(false);
        }

        // Handle update switch.
        if (switch_update_on_start_state) {
            switch_update_start.setChecked(true);
        } else {
            switch_update_start.setChecked(false);
        }

        // Handle exit switch.
        if (switch_ask_exit_state) {
            switch_ask_when_exit.setChecked(true);
        } else {
            switch_ask_when_exit.setChecked(false);
        }


    }
}
