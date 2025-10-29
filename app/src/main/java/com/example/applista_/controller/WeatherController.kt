package com.example.applista_.controller

import com.example.applista_.model.WeatherResponse
import com.example.applista_.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherController {

    suspend fun getWeatherByCapital(capital: String): Result<WeatherResponse> = withContext(Dispatchers.IO) {
        try {
            val response = RetrofitClient.weatherApiService.getCurrentWeather(
                RetrofitClient.WEATHER_API_KEY,
                capital
            )
            if (response.isSuccessful) {
                Result.success(response.body() ?: throw Exception("Empty response"))
            } else {
                when (response.code()) {
                    400 -> Result.failure(Exception("Ciudad no encontrada"))
                    401 -> Result.failure(Exception("API Key inválida"))
                    403 -> Result.failure(Exception("Acceso denegado"))
                    else -> Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}