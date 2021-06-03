package me.eduardo.shared.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import me.eduardo.shared.network.WeatherAPI
import me.eduardo.shared.network.responses.current.WeatherNowResponse

class CurrentTemperatureVMBase{

    private val api = WeatherAPI()
    private val _weather =  MutableStateFlow<WeatherNowResponse?>(null)
    val weather get() = _weather

    private val _error =  MutableStateFlow<Boolean>(false)
    val error get() = _error


    suspend fun getWeatherData(latitude: Double, longitude: Double){
        try {
            val data = api.getCurrentWeather(latitude, longitude)
            _weather.value = data
        }catch (err: Exception){
            _error.value = true
        }
    }

}