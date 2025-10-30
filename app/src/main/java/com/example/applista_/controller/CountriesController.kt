package com.example.applista_.controller

import com.example.applista_.model.Country
import com.example.applista_.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Controlador encargado de manejar la lógica relacionada con los países (API y datos)
class CountriesController {

    // Obtiene todos los países desde la API con validaciones básicas
    suspend fun getAllCountries(): Result<List<Country>> = withContext(Dispatchers.IO) {
        try {
            val response = RetrofitClient.countriesApiService.getAllCountries()

            // Verifica que la respuesta haya sido exitosa
            if (response.isSuccessful) {
                val countries = response.body() ?: emptyList()

                // Validación: la lista no debe venir vacía
                if (countries.isEmpty()) {
                    Result.failure(Exception("No se encontraron países."))
                } else {
                    Result.success(countries)
                }
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            // Captura errores de conexión u otros
            Result.failure(Exception("Error al obtener países: ${e.message}", e))
        }
    }

    // Obtiene los países filtrados por región con validaciones
    suspend fun getCountriesByRegion(region: String): Result<List<Country>> = withContext(Dispatchers.IO) {
        try {
            // Validación: el nombre de la región no debe ser vacío o inválido
            if (region.isBlank()) {
                return@withContext Result.failure(Exception("La región no puede estar vacía."))
            }

            val response = RetrofitClient.countriesApiService.getCountriesByRegion(region)
            if (response.isSuccessful) {
                val countries = response.body() ?: emptyList()
                if (countries.isEmpty()) {
                    Result.failure(Exception("No se encontraron países en la región $region."))
                } else {
                    Result.success(countries)
                }
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error al obtener países por región: ${e.message}", e))
        }
    }

    // Devuelve una lista de regiones predefinidas (validación: ninguna vacía)
    fun getRegions(): List<String> {
        val regions = listOf("Africa", "Americas", "Asia", "Europe", "Oceania")
        require(regions.all { it.isNotBlank() }) { "Hay una región vacía en la lista." }
        return regions
    }
}
