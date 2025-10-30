package com.example.applista_.network

import com.example.applista_.model.Country
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

// Servicio de Retrofit para obtener información de países
interface CountriesApiService {

    // Endpoint: https://restcountries.com/v3.1/all
    // Devuelve un Response que contiene una lista de Country
    @GET("v3.1/all")
    suspend fun getAllCountries(): Response<List<Country>>


    // @Path("region") reemplaza {region} en la URL
    //https://restcountries.com/v3.1/region/Europe
    @GET("v3.1/region/{region}")
    suspend fun getCountriesByRegion(@Path("region") region: String): Response<List<Country>>
}
