package me.eduardo.shared.network.responses.current

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Main(
    @SerialName("feels_like")
    val feels_like: Double,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("pressure")
    val pressure: Int,
    @SerialName("temp")
    val temp: Double,
    @SerialName("temp_max")
    val temp_max: Double,
    @SerialName("temp_min")
    val temp_min: Double
)