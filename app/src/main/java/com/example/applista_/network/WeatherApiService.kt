package com.example.applista_.network

import com.example.applista_.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    // obtiene el clima actual de una ciudad
    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String, // API Key
        @Query("q") query: String     // ciudad o ubicación
    ): Response<WeatherResponse>
}
