package me.eduardo.androidApp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.eduardo.shared.viewmodel.PrevisionTemperatureVMBase

class PrevisionTempViewModel(application: Application) : AndroidViewModel(application) {

    val baseVM = PrevisionTemperatureVMBase()

    fun getWeather(lat: Double, lon: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            baseVM.getWeatherData(lat, lon)
        }
    }

}