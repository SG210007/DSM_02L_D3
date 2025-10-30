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

class CountryDetailActivity : AppCompatActivity() {

    private lateinit var country: Country
    private val weatherController = WeatherController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_detail)

        // obtengo país enviado desde CountriesActivity
        country = intent.getSerializableExtra("COUNTRY") as? Country ?: run {
            Toast.makeText(this, "No hay datos del país", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        initViews()
        loadCountryData()
        loadWeatherData()
    }

    private fun initViews() {
        // título con nombre del país o genérico
        supportActionBar?.title = country.name?.common ?: "Detalles del País"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun loadCountryData() {
        // bandera
        val flagImageView: ImageView = findViewById(R.id.flagImageView)
        country.flags?.png?.let { flagUrl ->
            Glide.with(this)
                .load(flagUrl)
                .into(flagImageView)
        }

        // datos básicos
        findViewById<TextView>(R.id.nameTextView).text = country.name?.official ?: "N/A"
        findViewById<TextView>(R.id.capitalTextView).text = country.capital?.firstOrNull() ?: "N/A"
        findViewById<TextView>(R.id.regionTextView).text =
            "${country.region ?: "N/A"} - ${country.subregion ?: "N/A"}"
        findViewById<TextView>(R.id.populationTextView).text = formatPopulation(country.population ?: 0)
        findViewById<TextView>(R.id.codesTextView).text =
            "Códigos: ${country.alpha2Code ?: "N/A"} / ${country.alpha3Code ?: "N/A"}"

        // monedas
        val currenciesText = country.currencies?.entries?.joinToString(", ") {
            "${it.value.name} (${it.value.symbol ?: ""})"
        } ?: "N/A"
        findViewById<TextView>(R.id.currenciesTextView).text = currenciesText

        // idiomas
        val languagesText = country.languages?.values?.joinToString(", ") ?: "N/A"
        findViewById<TextView>(R.id.languagesTextView).text = languagesText

        // coordenadas
        val coordinatesText = country.latlng?.let {
            "Lat: ${it.getOrNull(0)}, Lng: ${it.getOrNull(1)}"
        } ?: "N/A"
        findViewById<TextView>(R.id.coordinatesTextView).text = coordinatesText
    }

    private fun loadWeatherData() {
        // obtengo capital
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
                "${it.tempC ?: "N/A"}°C / ${it.tempF ?: "N/A"}°F"
            findViewById<TextView>(R.id.conditionTextView).text = it.condition?.text ?: "N/A"
            findViewById<TextView>(R.id.windTextView).text =
                "Viento: ${it.windKph ?: "N/A"} kph / ${it.windMph ?: "N/A"} mph"
            findViewById<TextView>(R.id.humidityTextView).text =
                "Humedad: ${it.humidity ?: "N/A"}%"
            findViewById<TextView>(R.id.feelsLikeTextView).text =
                "Sensación: ${it.feelsLikeC ?: "N/A"}°C"

            // icono del clima
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
