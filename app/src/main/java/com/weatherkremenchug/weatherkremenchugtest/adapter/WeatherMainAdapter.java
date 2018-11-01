package com.weatherkremenchug.weatherkremenchugtest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weatherkremenchug.weatherkremenchugtest.R;
import com.weatherkremenchug.weatherkremenchugtest.data.ListData;
import com.weatherkremenchug.weatherkremenchugtest.data.WeatherForecast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

public class WeatherMainAdapter extends RecyclerView.Adapter<WeatherMainAdapter.ViewHolder> {

    private List<ListData> items;
    private Context context;

    public WeatherMainAdapter(Context context, List<ListData> items) {
        this.items = items;
        this.context = context;
    }


    @NonNull
    @Override
    public WeatherMainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_weather_main = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_screen, parent, false);
        return new ViewHolder(view_weather_main);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherMainAdapter.ViewHolder holder, int position) {

        // Get icon from openweathermap site.
        //String icon = items.get(position).weather.get(0).icon;
        //String iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";

         Random rnd = new Random();
         int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        holder.date.setText(items.get(position).getDt_txt());
        holder.temp.setText(items.get(position).main.getTemp());
        holder.press.setText(items.get(position).main.getPressure());

        if (items.get(position).weather.get(0).getIcon().equals("04d")) {
            holder.image_weather.setImageResource(R.drawable.ic_wi_cloudy);
        } else if (items.get(position).weather.get(0).getIcon().equals("02d")) {
            holder.image_weather.setImageResource(R.drawable.ic_wi_day_cloudy);
        } else if (items.get(position).weather.get(0).getIcon().equals("01d")) {
            holder.image_weather.setImageResource(R.drawable.ic_wi_day_sunny);
        } else if (items.get(position).weather.get(0).getIcon().equals("03d")) {
            holder.image_weather.setImageResource(R.drawable.ic_wi_cloud);
        } else if (items.get(position).weather.get(0).getIcon().equals("09d")) {
            holder.image_weather.setImageResource(R.drawable.ic_wi_rain);
        } else if (items.get(position).weather.get(0).getIcon().equals("10d")) {
            holder.image_weather.setImageResource(R.drawable.ic_wi_day_rain);
        } else if (items.get(position).weather.get(0).getIcon().equals("11d")) {
            holder.image_weather.setImageResource(R.drawable.ic_wi_thunderstorm);
        } else if (items.get(position).weather.get(0).getIcon().equals("13d")) {
            holder.image_weather.setImageResource(R.drawable.ic_wi_snow);
        } else if (items.get(position).weather.get(0).getIcon().equals("50d")) {
            holder.image_weather.setImageResource(R.drawable.ic_wi_day_haze);
        } else if (items.get(position).weather.get(0).getIcon().equals("01n")) {
            holder.image_weather.setImageResource(R.drawable.ic_wi_night_clear);
        } else if (items.get(position).weather.get(0).getIcon().equals("02n")) {
            holder.image_weather.setImageResource(R.drawable.ic_wi_night_alt_partly_cloudy);
        } else if (items.get(position).weather.get(0).getIcon().equals("03n")) {
            holder.image_weather.setImageResource(R.drawable.ic_wi_night_alt_cloudy);
        } else if (items.get(position).weather.get(0).getIcon().equals("04n")) {
            holder.image_weather.setImageResource(R.drawable.ic_wi_cloud);
        } else if (items.get(position).weather.get(0).getIcon().equals("09n")) {
            holder.image_weather.setImageResource(R.drawable.ic_wi_rain);
        } else if (items.get(position).weather.get(0).getIcon().equals("10n")) {
            holder.image_weather.setImageResource(R.drawable.ic_wi_night_alt_rain);
        } else if (items.get(position).weather.get(0).getIcon().equals("11n")) {
            holder.image_weather.setImageResource(R.drawable.ic_wi_thunderstorm);
        } else if (items.get(position).weather.get(0).getIcon().equals("13n")) {
            holder.image_weather.setImageResource(R.drawable.ic_wi_snow);
        } else if (items.get(position).weather.get(0).getIcon().equals("50d")) {
            holder.image_weather.setImageResource(R.drawable.ic_wi_night_fog);
        }

        // Load icon from openweathermap site into ImageView.
        //Glide.with(context).load(iconUrl).into(holder.image_weather);

        holder.cardView.setBackgroundColor(color);

    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView image_weather;
        TextView date;
        TextView temp;
        TextView press;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_main);
            image_weather = itemView.findViewById(R.id.image_weather);
            date = itemView.findViewById(R.id.day_month_year);
            temp = itemView.findViewById(R.id.temperature_number);
            press = itemView.findViewById(R.id.pressure_number);

        }
    }
}
