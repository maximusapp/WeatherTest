package com.weather.weathermain.data.network.service

class ServiceGenerator {
    companion object {
        var endPoint: String = ""
    }

    inline fun <reified S> createService(endPoint: String): S {
        Companion.endPoint = endPoint
    }

}
