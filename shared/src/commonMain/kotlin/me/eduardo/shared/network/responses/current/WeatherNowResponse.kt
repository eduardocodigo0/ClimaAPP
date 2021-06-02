package me.eduardo.shared.network.responses.current

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherNowResponse(
    @SerialName("base")
    val base: String,
    @SerialName("cod")
    val cod: Int,
    @SerialName("dt")
    val dt: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("main")
    @Contextual
    val main: Main,
    @SerialName("name")
    val name: String,
    @SerialName("weather")
    @Contextual
    val weather: List<@Contextual Weather>

)