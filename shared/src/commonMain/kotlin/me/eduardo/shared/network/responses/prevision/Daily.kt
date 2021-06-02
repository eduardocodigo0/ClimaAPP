package me.eduardo.shared.network.responses.prevision

import kotlinx.serialization.Serializable

@Serializable
data class Daily(
    val dew_point: Double,
    val dt: Int,
    val humidity: Int,
    val rain: Double = 0.0,
    val temp: Temp,
    val weather: List<Weather>,
)