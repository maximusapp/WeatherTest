package com.weather.weathermain.data.network.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import com.weather.weathermain.WeatherApplication
import com.weather.weathermain.utils.constants.ISO_8601_FORMAT
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceGenerator {
    companion object {
        var endPoint: String = ""
        var retrofit: Retrofit? = null
        private const val TIME_OUT = 30L

        private val gson: Gson by lazy {
            GsonBuilder().setDateFormat(ISO_8601_FORMAT).create()
        }

        inline fun <reified S> createService(endPoint: String): S {
            Companion.endPoint = endPoint
            return getRetrofit(true).create(S::class.java)
        }

        fun getRetrofit(boolean: Boolean): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(endPoint)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                        .client(buildClient())
                        .build()
            }
            return retrofit!!
        }

        private fun buildClient(): OkHttpClient {
            val client = OkHttpClient.Builder()
            client
                    .addInterceptor(ChuckInterceptor(WeatherApplication.instance.applicationContext))
                    .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(TIME_OUT, TimeUnit.SECONDS)

            return client.build()
        }

    }

}
