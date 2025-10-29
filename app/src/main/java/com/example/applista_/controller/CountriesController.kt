package com.example.applista_.controller

import com.example.applista_.model.Country
import com.example.applista_.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CountriesController {

    suspend fun getAllCountries(): Result<List<Country>> = withContext(Dispatchers.IO) {
        try {
            val response = RetrofitClient.countriesApiService.getAllCountries()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCountriesByRegion(region: String): Result<List<Country>> = withContext(Dispatchers.IO) {
        try {
            val response = RetrofitClient.countriesApiService.getCountriesByRegion(region)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getRegions(): List<String> {
        return listOf("Africa", "Americas", "Asia", "Europe", "Oceania")
    }
}