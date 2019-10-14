package com.weatherkremenchug.weathermain.adapter

import android.graphics.Color
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.weatherkremenchug.weathermain.R
import com.weatherkremenchug.weathermain.data.ListData

import java.util.Random

class WeatherMainAdapter(private val items: List<ListData>?) : RecyclerView.Adapter<WeatherMainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view_weather_main = LayoutInflater.from(parent.context).inflate(R.layout.item_main_screen, parent, false)
        return ViewHolder(view_weather_main)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Get icon from openweathermap site.
        //String icon = items.get(position).weather.get(0).icon;
        //String iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";

        val rnd = Random()
        val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))

        holder.date.text = items!![position].dt_txt
        holder.temp.text = items[position].main?.temp
        holder.press.text = items[position].main?.pressure

        when {
            items[position].weather[0].icon == "04d" -> holder.imageWeather.setImageResource(R.drawable.ic_wi_cloudy)
            items[position].weather[0].icon == "02d" -> holder.imageWeather.setImageResource(R.drawable.ic_wi_day_cloudy)
            items[position].weather[0].icon == "01d" -> holder.imageWeather.setImageResource(R.drawable.ic_wi_day_sunny)
            items[position].weather[0].icon == "03d" -> holder.imageWeather.setImageResource(R.drawable.ic_wi_cloud)
            items[position].weather[0].icon == "09d" -> holder.imageWeather.setImageResource(R.drawable.ic_wi_rain)
            items[position].weather[0].icon == "10d" -> holder.imageWeather.setImageResource(R.drawable.ic_wi_day_rain)
            items[position].weather[0].icon == "11d" -> holder.imageWeather.setImageResource(R.drawable.ic_wi_thunderstorm)
            items[position].weather[0].icon == "13d" -> holder.imageWeather.setImageResource(R.drawable.ic_wi_snow)
            items[position].weather[0].icon == "50d" -> holder.imageWeather.setImageResource(R.drawable.ic_wi_day_haze)
            items[position].weather[0].icon == "01n" -> holder.imageWeather.setImageResource(R.drawable.ic_wi_night_clear)
            items[position].weather[0].icon == "02n" -> holder.imageWeather.setImageResource(R.drawable.ic_wi_night_alt_partly_cloudy)
            items[position].weather[0].icon == "03n" -> holder.imageWeather.setImageResource(R.drawable.ic_wi_night_alt_cloudy)
            items[position].weather[0].icon == "04n" -> holder.imageWeather.setImageResource(R.drawable.ic_wi_cloud)
            items[position].weather[0].icon == "09n" -> holder.imageWeather.setImageResource(R.drawable.ic_wi_rain)
            items[position].weather[0].icon == "10n" -> holder.imageWeather.setImageResource(R.drawable.ic_wi_night_alt_rain)
            items[position].weather[0].icon == "11n" -> holder.imageWeather.setImageResource(R.drawable.ic_wi_thunderstorm)
            items[position].weather[0].icon == "13n" -> holder.imageWeather.setImageResource(R.drawable.ic_wi_snow)
            items[position].weather[0].icon == "50d" -> holder.imageWeather.setImageResource(R.drawable.ic_wi_night_fog)
        }

         // Load icon from openweathermap site into ImageView.
        //Glide.with(context).load(iconUrl).into(holder.imageWeather);

        // Load icon from openweathermap site into ImageView.
        //Glide.with(context).load(iconUrl).into(holder.imageWeather);

        holder.cardView.setBackgroundColor(color)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         var cardView: CardView = itemView.findViewById(R.id.card_main)
         var imageWeather: ImageView = itemView.findViewById(R.id.image_weather)
         var date: TextView = itemView.findViewById(R.id.day_month_year)
         var temp: TextView = itemView.findViewById(R.id.temperature_number)
         var press: TextView = itemView.findViewById(R.id.pressure_number)
    }
}
//113