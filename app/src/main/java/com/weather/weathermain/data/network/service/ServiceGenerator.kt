package com.weather.weathermain.data.network.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.weather.weathermain.utils.constants.ISO_8601_FORMAT
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceGenerator {
    companion object {
        var endPoint: String = ""
        var retrofit: Retrofit? = null

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
                        .build()
            }
            return retrofit!!
        }

    }

}
