package me.eduardo.shared.viewmodel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import me.eduardo.shared.network.WeatherAPI
import me.eduardo.shared.network.responses.current.WeatherNowResponse

class CurrentTemperatureVMBase{

    private val api = WeatherAPI()
    @ExperimentalCoroutinesApi
    private val _weather =  MutableStateFlow<WeatherNowResponse?>(null)
    @ExperimentalCoroutinesApi
    val weather get() = _weather


    @ExperimentalCoroutinesApi
    suspend fun getWeatherData(latitude: Double, longitude: Double){

        val data = api.getCurrentWeather(latitude, longitude)
        _weather.value = data
    }

}