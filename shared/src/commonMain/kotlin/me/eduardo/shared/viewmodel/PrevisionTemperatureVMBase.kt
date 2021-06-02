package me.eduardo.shared.viewmodel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import me.eduardo.shared.network.WeatherAPI
import me.eduardo.shared.network.responses.current.WeatherNowResponse
import me.eduardo.shared.network.responses.prevision.WeatherPrevisionResponse

class PrevisionTemperatureVMBase {

    private val api = WeatherAPI()
    @ExperimentalCoroutinesApi
    private val _weather =  MutableStateFlow<WeatherPrevisionResponse?>(null)
    @ExperimentalCoroutinesApi
    val weather get() = _weather


    @ExperimentalCoroutinesApi
    suspend fun getWeatherData(latitude: Double, longitude: Double){

        val data = api.getWeatherPrevision(latitude, longitude)
        _weather.value = data
    }
}