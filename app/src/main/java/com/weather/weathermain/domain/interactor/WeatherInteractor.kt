package com.weather.weathermain.domain.interactor

//interface IWeatherInteractor {
//    companion object {
//        fun getInstance(weather: IWeatherRepository = IWeatherRepository.getInstance()) = WeatherInteractor(weather)
//    }
//
//    fun getCurrentWeatherData(latitude: Double, longitude: Double, units: String, appid: String): Single<WeatherOnTodayResponse>
//
//}
//
//class WeatherInteractor (private val weatherRepository: IWeatherRepository):
//        IWeatherInteractor {
//    override fun getCurrentWeatherData(latitude: Double, longitude: Double, units: String, appid: String): Single<WeatherOnTodayResponse> {
//        return weatherRepository.getCurrentWeatherData(latitude, longitude, units, appid)
//    }
//}