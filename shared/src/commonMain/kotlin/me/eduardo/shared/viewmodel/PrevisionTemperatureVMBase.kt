package me.eduardo.shared.viewmodel


import kotlinx.coroutines.flow.MutableStateFlow
import me.eduardo.shared.network.WeatherAPI
import me.eduardo.shared.network.responses.prevision.WeatherPrevisionResponse

class PrevisionTemperatureVMBase {

    private val api = WeatherAPI()
    private val _weather =  MutableStateFlow<WeatherPrevisionResponse?>(null)
    val weather get() = _weather

    private val _error =  MutableStateFlow<Boolean>(false)
    val error get() = _error


    suspend fun getWeatherData(latitude: Double, longitude: Double){
        try {
            val data = api.getWeatherPrevision(latitude, longitude)
            _weather.value = data
        }catch (err: Exception){
            _error.value = true
        }

    }
}