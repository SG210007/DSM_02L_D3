package com.example.applista_.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name") val name: CountryName?,
    @SerializedName("capital") val capital: List<String>?,
    @SerializedName("region") val region: String?,
    @SerializedName("subregion") val subregion: String?,
    @SerializedName("population") val population: Long?,
    @SerializedName("flags") val flags: CountryFlags?,
    @SerializedName("cca2") val alpha2Code: String?,
    @SerializedName("cca3") val alpha3Code: String?,
    @SerializedName("currencies") val currencies: Map<String, Currency>?,
    @SerializedName("languages") val languages: Map<String, String>?,
    @SerializedName("latlng") val latlng: List<Double>?
)

data class CountryName(
    @SerializedName("common") val common: String?,
    @SerializedName("official") val official: String?
)

data class CountryFlags(
    @SerializedName("png") val png: String?,
    @SerializedName("svg") val svg: String?
)

data class Currency(
    @SerializedName("name") val name: String?,
    @SerializedName("symbol") val symbol: String?
)