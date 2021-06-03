package me.eduardo.androidApp.ui

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.eduardo.shared.viewmodel.CurrentTemperatureVMBase


class CurrentTempViewModel(application: Application) : AndroidViewModel(application) {

    val baseVM = CurrentTemperatureVMBase()

    fun getWeather(lat: Double, lon: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            baseVM.getWeatherData(lat, lon)
        }
    }

}