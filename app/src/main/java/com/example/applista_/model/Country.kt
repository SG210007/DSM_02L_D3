package com.example.applista_.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

// Modelo principal de un país
data class Country(
    @SerializedName("name") val name: CountryName?,               // Nombre común y oficial
    @SerializedName("capital") val capital: List<String>?,        // Lista de capitales (puede ser más de una)
    @SerializedName("region") val region: String?,               // Región principal
    @SerializedName("subregion") val subregion: String?,         // Subregión
    @SerializedName("population") val population: Long?,         // Cantidad de habitantes
    @SerializedName("flags") val flags: CountryFlags?,           // Imágenes de la bandera
    @SerializedName("cca2") val alpha2Code: String?,             // Código ISO 2 letras
    @SerializedName("cca3") val alpha3Code: String?,             // Código ISO 3 letras
    @SerializedName("currencies") val currencies: Map<String, Currency>?, // Monedas oficiales
    @SerializedName("languages") val languages: Map<String, String>?,    // Idiomas oficiales
    @SerializedName("latlng") val latlng: List<Double>?          // Latitud y longitud
) : Serializable {

    // 🔹 Validaciones y helpers para usar en la app

    // Devuelve el nombre común o un valor por defecto
    fun getCommonName(): String = name?.common?.takeIf { it.isNotBlank() } ?: "Nombre no disponible"

    // Devuelve el nombre oficial o un valor por defecto
    fun getOfficialName(): String = name?.official?.takeIf { it.isNotBlank() } ?: "Nombre oficial no disponible"

    // Devuelve la capital principal o un valor por defecto
    fun getCapital(): String = capital?.firstOrNull()?.takeIf { it.isNotBlank() } ?: "Capital no disponible"

    // Devuelve la bandera en PNG o una cadena vacía
    fun getFlagUrl(): String = flags?.png?.takeIf { it.isNotBlank() } ?: ""

    // Devuelve la población en formato legible
    fun getPopulation(): String = population?.toString() ?: "Desconocida"

    // Devuelve la lista de monedas como texto
    fun getCurrencies(): String =
        currencies?.values?.joinToString(", ") { it.name ?: "N/A" } ?: "Sin moneda"

    // Devuelve la lista de idiomas como texto
    fun getLanguages(): String =
        languages?.values?.joinToString(", ") ?: "Sin idiomas"

    // Devuelve latitud o 0.0 si no existe
    fun getLatitude(): Double = latlng?.getOrNull(0) ?: 0.0

    // Devuelve longitud o 0.0 si no existe
    fun getLongitude(): Double = latlng?.getOrNull(1) ?: 0.0
}

// Modelo del nombre del país
data class CountryName(
    @SerializedName("common") val common: String?,
    @SerializedName("official") val official: String?
) : Serializable

// Modelo de las banderas del país
data class CountryFlags(
    @SerializedName("png") val png: String?,
    @SerializedName("svg") val svg: String?
) : Serializable

// Modelo de la moneda
data class Currency(
    @SerializedName("name") val name: String?,
    @SerializedName("symbol") val symbol: String?
) : Serializable
