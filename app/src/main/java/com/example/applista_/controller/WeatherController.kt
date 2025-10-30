package com.example.applista_.controller

import com.example.applista_.model.WeatherResponse
import com.example.applista_.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherController {

    suspend fun getWeatherByCapital(capital: String): Result<WeatherResponse> = withContext(Dispatchers.IO) {
        try {
            // Validar entrada
            if (capital.isBlank()) {
                return@withContext Result.failure(Exception("El nombre de la ciudad no puede estar vacío"))
            }

            val response = RetrofitClient.weatherApiService.getCurrentWeather(
                RetrofitClient.WEATHER_API_KEY,
                capital
            )

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("La respuesta del servidor está vacía"))
                }
            } else {
                val message = when (response.code()) {
                    400 -> "Ciudad no encontrada o parámetro inválido"
                    401 -> "API Key inválida o expirada"
                    403 -> "Acceso denegado"
                    404 -> "Recurso no encontrado"
                    500 -> "Error interno del servidor"
                    else -> "Error HTTP ${response.code()}: ${response.message()}"
                }
                Result.failure(Exception(message))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión o red: ${e.message}"))
        }
    }
}
