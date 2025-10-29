package com.example.applista_.network

import com.example.applista_.model.Country
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesApiService {
    @GET("v3.1/all")
    suspend fun getAllCountries(): Response<List<Country>>

    @GET("v3.1/region/{region}")
    suspend fun getCountriesByRegion(@Path("region") region: String): Response<List<Country>>
}