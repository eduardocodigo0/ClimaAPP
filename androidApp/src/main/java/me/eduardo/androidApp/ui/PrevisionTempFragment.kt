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
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.eduardo.androidApp.R
import me.eduardo.androidApp.adapter.PrevisionTempAdapter
import me.eduardo.androidApp.databinding.FragmentPrevisionTempBinding


class PrevisionTempFragment : Fragment() {

    private lateinit var viewModel: PrevisionTempViewModel
    private var _binding: FragmentPrevisionTempBinding? = null
    private val binding get() = _binding!!
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var weatherJob: Job? = null
    private var errorJob: Job? = null

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPrevisionTempBinding.inflate(inflater, container, false)

        binding.rvPrevision.visibility = View.GONE
        binding.pbPrevLoad.visibility = View.VISIBLE

        viewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
                .create(PrevisionTempViewModel::class.java)


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
        weatherJob = lifecycleScope.launch() {
            viewModel.baseVM.weather.collect { data ->

                data?.let {
                    binding.rvPrevision.apply {
                        adapter = PrevisionTempAdapter(it.daily)
                    }
                    binding.rvPrevision.visibility = View.VISIBLE
                    binding.pbPrevLoad.visibility = View.GONE
                }
            }
        }

        errorJob = lifecycleScope.launch {
            viewModel.baseVM.error.collect { error ->
                if(error){
                    Toast.makeText(context, R.string.no_internet_alert, Toast.LENGTH_LONG).show()
                }
            }
        }



        super.onStart()
    }

    override fun onStop() {
        weatherJob?.cancel()
        errorJob?.cancel()
        super.onStop()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}