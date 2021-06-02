package me.eduardo.shared.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.roundToInt


fun Double.kelvinToCelsius() = (this - 273.15).roundToInt()

fun Int.toDate(): String{

    val time = Instant.fromEpochMilliseconds(this.toLong() * 1000)
    val localTime = time.toLocalDateTime(TimeZone.currentSystemDefault())

    val dateString = localTime.date.toString()
    val dateParts = dateString.split("-")

    return "${dateParts[2]}/${dateParts[1]}/${dateParts[0]}"
}