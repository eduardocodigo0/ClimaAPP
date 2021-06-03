package me.eduardo.shared.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import me.eduardo.shared.network.WeatherAPI
import me.eduardo.shared.network.responses.current.WeatherNowResponse

class CurrentTemperatureVMBase{

    private val api = WeatherAPI()
    private val _weather =  MutableStateFlow<WeatherNowResponse?>(null)
    val weather get() = _weather


    suspend fun getWeatherData(latitude: Double, longitude: Double){

        val data = api.getCurrentWeather(latitude, longitude)
        _weather.value = data
    }

}