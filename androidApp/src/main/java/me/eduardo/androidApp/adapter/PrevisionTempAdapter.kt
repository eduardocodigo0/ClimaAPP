package me.eduardo.androidApp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import me.eduardo.androidApp.R
import me.eduardo.androidApp.databinding.ListItemBinding
import me.eduardo.shared.network.WeatherAPI
import me.eduardo.shared.network.responses.prevision.Daily
import me.eduardo.shared.util.kelvinToCelsius
import me.eduardo.shared.util.toDate

class PrevisionTempAdapter(val weather: List<Daily>) :
    RecyclerView.Adapter<PrevissionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrevissionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return PrevissionViewHolder(view)
    }

    override fun onBindViewHolder(holder: PrevissionViewHolder, position: Int) {

        holder.bind(weather[position])

    }

    override fun getItemCount(): Int {
        return weather.size
    }
}


class PrevissionViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    private val binding = ListItemBinding.bind(item)

    fun bind(weather: Daily) {

        binding.tvPrevDate.text = weather.dt.toDate()
        binding.tvPrevMaxTemperature.text = "MAX: ${weather.temp.max.kelvinToCelsius()}°C"
        binding.tvPrevMinTemperature.text = "MIN: ${weather.temp.min.kelvinToCelsius()}°C"
        binding.tvPrevDescription.text = weather.weather[0].description
        binding.tvPrevMain.text = weather.weather[0].main
        Picasso.get().load(WeatherAPI.getIconEndPoint(weather.weather[0].icon))
            .into(binding.ivPrevWeatherIcon)
    }

}