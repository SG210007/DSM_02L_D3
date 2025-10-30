package com.example.applista_.model

import com.google.gson.annotations.SerializedName

// Modelo principal de la respuesta de clima
data class WeatherResponse(
    @SerializedName("current") val current: CurrentWeather?
) {

    // 🔹 Helpers para acceder a los datos de manera segura

    fun getTempC(): String = current?.tempC?.toString() ?: "N/D"
    fun getTempF(): String = current?.tempF?.toString() ?: "N/D"
    fun getFeelsLikeC(): String = current?.feelsLikeC?.toString() ?: "N/D"
    fun getWindKph(): String = current?.windKph?.toString() ?: "N/D"
    fun getWindMph(): String = current?.windMph?.toString() ?: "N/D"
    fun getHumidity(): String = current?.humidity?.toString() ?: "N/D"
    fun getConditionText(): String = current?.condition?.text?.takeIf { it.isNotBlank() } ?: "Desconocido"
    fun getConditionIcon(): String = current?.condition?.icon?.takeIf { it.isNotBlank() } ?: ""
}

// Modelo de los datos actuales del clima
data class CurrentWeather(
    @SerializedName("temp_c") val tempC: Double?,
    @SerializedName("temp_f") val tempF: Double?,
    @SerializedName("condition") val condition: WeatherCondition?,
    @SerializedName("wind_kph") val windKph: Double?,
    @SerializedName("wind_mph") val windMph: Double?,
    @SerializedName("humidity") val humidity: Int?,
    @SerializedName("feelslike_c") val feelsLikeC: Double?
)

// Modelo de la condición del clima
data class WeatherCondition(
    @SerializedName("text") val text: String?,
    @SerializedName("icon") val icon: String?
)
