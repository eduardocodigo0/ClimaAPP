package me.eduardo.androidApp.ui

import android.annotation.SuppressLint
import android.app.Application
import android.os.Looper
import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.location.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.eduardo.shared.viewmodel.PrevisionTemperatureVMBase

class PrevisionTempViewModel(application: Application) : AndroidViewModel(application){

    val baseVM = PrevisionTemperatureVMBase()


    fun getWeather(lat: Double, lon: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            baseVM.getWeatherData(lat, lon)
        }
    }


}