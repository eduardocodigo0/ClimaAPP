package me.eduardo.androidApp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.*
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.eduardo.androidApp.R
import me.eduardo.androidApp.databinding.FragmentCurrentTempBinding
import me.eduardo.shared.network.WeatherAPI
import me.eduardo.shared.util.kelvinToCelsius
import me.eduardo.shared.util.toDate


class CurrentTempFragment : Fragment() {

    private lateinit var viewModel: CurrentTempViewModel
    private var _binding: FragmentCurrentTempBinding? = null
    private val binding get() = _binding!!
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var weatherJob: Job? = null
    private var errorJob: Job? = null

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCurrentTempBinding.inflate(inflater)

        binding.tvCity.visibility = View.GONE
        binding.tvDescription.visibility = View.GONE
        binding.tvDate.visibility = View.GONE
        binding.tvFeels.visibility = View.GONE
        binding.tvHumidity.visibility = View.GONE
        binding.clCard.visibility = View.GONE
        binding.pbLoad.visibility = View.VISIBLE

        viewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
                .create(CurrentTempViewModel::class.java)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        val locationRequest = LocationRequest.create().apply {
            interval = 100
            fastestInterval = 50
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            maxWaitTime = 100
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {

                try {
                    viewModel.getWeather(p0.lastLocation.latitude, p0.lastLocation.longitude)
                } catch (err: Exception) {
                    Log.d("clima", "${err.message}")
                }

                super.onLocationResult(p0)
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

        return binding.root
    }

    override fun onStart() {
        weatherJob = lifecycleScope.launch {
            viewModel.baseVM.weather.collect { data ->
                data?.let {

                    binding.tvTemperature.text = "${it.main.temp.kelvinToCelsius()}°C"
                    binding.tvCity.text = it.name
                    binding.tvDescription.text = it.weather[0].description
                    binding.tvDate.text = it.dt.toDate()
                    binding.tvFeels.text = "Feels Like: ${it.main.feels_like.kelvinToCelsius()}°C"
                    binding.tvHumidity.text = "Humidity: ${it.main.humidity}%"
                    binding.tvMaxTemperature.text = "MAX: ${it.main.temp_max.kelvinToCelsius()}°C"
                    binding.tvMinTemperature.text = "MIN: ${it.main.temp_min.kelvinToCelsius()}°C"

                    Picasso.get().load(WeatherAPI.getIconEndPoint(it.weather[0].icon))
                        .into(binding.ivWeatherIcon)

                    binding.tvCity.visibility = View.VISIBLE
                    binding.tvDate.visibility = View.VISIBLE
                    binding.tvFeels.visibility = View.VISIBLE
                    binding.tvHumidity.visibility = View.VISIBLE
                    binding.clCard.visibility = View.VISIBLE
                    binding.tvDescription.visibility = View.VISIBLE
                    binding.pbLoad.visibility = View.GONE

                }
            }


        }
        errorJob = lifecycleScope.launch {
            viewModel.baseVM.error.collect { error ->
                if(error){
                    Toast.makeText(context,R.string.no_internet_alert, Toast.LENGTH_LONG).show()
                }
            }
        }



        super.onStart()
    }

    override fun onPause() {
        weatherJob?.cancel()
        errorJob?.cancel()
        super.onPause()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}