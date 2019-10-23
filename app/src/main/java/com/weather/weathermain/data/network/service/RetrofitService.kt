package com.weather.weathermain.data.network.service

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import com.weather.weathermain.BuildConfig
import com.weather.weathermain.WeatherApplication
import com.weather.weathermain.utils.constants.ISO_8601_FORMAT
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitService {
    companion object {
        var endPoint: String = ""
        var retrofit: Retrofit? = null
        private const val TIME_OUT = 30L
        private const val NETWORK = "NETWORK"

        private val gson: Gson by lazy {
            GsonBuilder().setDateFormat(ISO_8601_FORMAT).create()
        }

        inline fun <reified S> createService(endPoint: String): S {
            val shouldAddInterceptor = true

            Companion.endPoint = endPoint
            return getRetrofit(shouldAddInterceptor).create(S::class.java)
        }

        fun loggingInterceptor(addCustomHeaders: Boolean): LoggingInterceptor? {
            val builder = LoggingInterceptor.Builder()
                    .loggable(BuildConfig.DEBUG)
                    .setLevel(Level.BASIC)
                    .log(Log.INFO)
                    .request(NETWORK)
                    .response(NETWORK)
            return builder.build()
        }

        fun getRetrofit(shouldAddInterceptor: Boolean): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(endPoint)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                        .client(buildClient(shouldAddInterceptor))
                        .build()
            }
            return retrofit!!
        }

        private fun buildClient(shouldAddCustomHeaders: Boolean = true): OkHttpClient {
            val client = OkHttpClient.Builder()
            client
                    .addInterceptor(loggingInterceptor(shouldAddCustomHeaders) as Interceptor)
                   // .addInterceptor(ChuckInterceptor(WeatherApplication.instance.applicationContext))
                    .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(TIME_OUT, TimeUnit.SECONDS)

            return client.build()
        }

    }

}
