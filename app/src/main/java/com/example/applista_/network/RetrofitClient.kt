package com.example.applista_.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    // URL base de países
    private const val COUNTRIES_BASE_URL = "https://restcountries.com/"
    // URL base del clima
    private const val WEATHER_BASE_URL = "https://api.weatherapi.com/v1/"

    // API Key de WeatherAPI
    const val WEATHER_API_KEY = "8b86ed64ffc54605a0711947253010"

    // logging para ver lo que manda y recibe
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // cliente HTTP con timeout y logging
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // Retrofit para países
    private val countriesRetrofit = Retrofit.Builder()
        .baseUrl(COUNTRIES_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Retrofit para clima
    private val weatherRetrofit = Retrofit.Builder()
        .baseUrl(WEATHER_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // servicio de países
    val countriesApiService: CountriesApiService by lazy {
        countriesRetrofit.create(CountriesApiService::class.java)
    }

    // servicio del clima
    val weatherApiService: WeatherApiService by lazy {
        weatherRetrofit.create(WeatherApiService::class.java)
    }
}
