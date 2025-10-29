package com.example.applista_.view

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.applista_.R
import com.example.applista_.controller.WeatherController
import com.example.applista_.model.Country
import com.example.applista_.model.WeatherResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountryDetailActivity : AppCompatActivity() {

    private lateinit var country: Country
    private val weatherController = WeatherController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_detail)

        country = intent.getSerializableExtra("COUNTRY") as? Country ?: return

        initViews()
        loadCountryData()
        loadWeatherData()
    }

    private fun initViews() {
        supportActionBar?.title = country.name?.common ?: "Detalles del País"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun loadCountryData() {
        // Bandera
        val flagImageView: ImageView = findViewById(R.id.flagImageView)
        country.flags?.png?.let { flagUrl ->
            Glide.with(this)
                .load(flagUrl)
                .into(flagImageView)
        }

        // Datos básicos
        findViewById<TextView>(R.id.nameTextView).text = country.name?.official ?: "N/A"
        findViewById<TextView>(R.id.capitalTextView).text = country.capital?.firstOrNull() ?: "N/A"
        findViewById<TextView>(R.id.regionTextView).text = "${country.region ?: "N/A"} - ${country.subregion ?: "N/A"}"
        findViewById<TextView>(R.id.populationTextView).text = formatPopulation(country.population ?: 0)
        findViewById<TextView>(R.id.codesTextView).text = "Códigos: ${country.alpha2Code ?: "N/A"} / ${country.alpha3Code ?: "N/A"}"

        // Monedas
        val currenciesText = country.currencies?.entries?.joinToString(", ") {
            "${it.value.name} (${it.value.symbol ?: ""})"
        } ?: "N/A"
        findViewById<TextView>(R.id.currenciesTextView).text = currenciesText

        // Idiomas
        val languagesText = country.languages?.values?.joinToString(", ") ?: "N/A"
        findViewById<TextView>(R.id.languagesTextView).text = languagesText

        // Coordenadas
        val coordinatesText = country.latlng?.let {
            "Lat: ${it.getOrNull(0)}, Lng: ${it.getOrNull(1)}"
        } ?: "N/A"
        findViewById<TextView>(R.id.coordinatesTextView).text = coordinatesText
    }

    private fun loadWeatherData() {
        val capital = country.capital?.firstOrNull() ?: run {
            showWeatherError("No hay capital disponible")
            return
        }

        val progressBar: ProgressBar = findViewById(R.id.weatherProgressBar)
        progressBar.visibility = ProgressBar.VISIBLE

        CoroutineScope(Dispatchers.Main).launch {
            val result = weatherController.getWeatherByCapital(capital)

            progressBar.visibility = ProgressBar.GONE

            when {
                result.isSuccess -> {
                    val weather = result.getOrNull()
                    showWeatherData(weather)
                }
                result.isFailure -> {
                    showWeatherError(result.exceptionOrNull()?.message ?: "Error desconocido")
                }
            }
        }
    }

    private fun showWeatherData(weather: WeatherResponse?) {
        val currentWeather = weather?.current
        val weatherLayout: LinearLayout = findViewById(R.id.weatherLayout)
        val errorTextView: TextView = findViewById(R.id.weatherErrorTextView)

        weatherLayout.visibility = LinearLayout.VISIBLE
        errorTextView.visibility = TextView.GONE

        currentWeather?.let {
            findViewById<TextView>(R.id.temperatureTextView).text =
                "${it.tempC}°C / ${it.tempF}°F"
            findViewById<TextView>(R.id.conditionTextView).text =
                it.condition?.text ?: "N/A"
            findViewById<TextView>(R.id.windTextView).text =
                "Viento: ${it.windKph} kph / ${it.windMph} mph"
            findViewById<TextView>(R.id.humidityTextView).text =
                "Humedad: ${it.humidity}%"
            findViewById<TextView>(R.id.feelsLikeTextView).text =
                "Sensación: ${it.feelsLikeC}°C"

            // Cargar icono del clima
            it.condition?.icon?.let { iconUrl ->
                val weatherIcon: ImageView = findViewById(R.id.weatherIconImageView)
                Glide.with(this)
                    .load("https:${iconUrl}")
                    .into(weatherIcon)
            }
        }
    }

    private fun showWeatherError(message: String) {
        val weatherLayout: LinearLayout = findViewById(R.id.weatherLayout)
        val errorTextView: TextView = findViewById(R.id.weatherErrorTextView)

        weatherLayout.visibility = LinearLayout.GONE
        errorTextView.visibility = TextView.VISIBLE
        errorTextView.text = "Error del clima: $message"
    }

    private fun formatPopulation(population: Long): String {
        return String.format("%,d", population)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}