package com.example.applista_.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val COUNTRIES_BASE_URL = "https://restcountries.com/"
    private const val WEATHER_BASE_URL = "http://api.weatherapi.com/v1/"

    // TODO: Reemplaza con tu API Key de WeatherAPI
    const val WEATHER_API_KEY = "cb6fa89f4f7a42adbf6205310252910"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val countriesRetrofit = Retrofit.Builder()
        .baseUrl(COUNTRIES_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val weatherRetrofit = Retrofit.Builder()
        .baseUrl(WEATHER_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val countriesApiService: CountriesApiService by lazy {
        countriesRetrofit.create(CountriesApiService::class.java)
    }

    val weatherApiService: WeatherApiService by lazy {
        weatherRetrofit.create(WeatherApiService::class.java)
    }
}