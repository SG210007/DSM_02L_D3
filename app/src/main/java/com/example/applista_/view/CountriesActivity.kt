package com.example.applista_.view

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applista_.R
import com.example.applista_.adapter.CountriesAdapter
import com.example.applista_.controller.CountriesController
import com.example.applista_.model.Country
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountriesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private val countriesController = CountriesController()
    private lateinit var region: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries)

        region = intent.getStringExtra("REGION") ?: ""
        initViews()
        loadCountries()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.countriesRecyclerView)
        progressBar = findViewById(R.id.progressBar)

        supportActionBar?.title = "Países de $region"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun loadCountries() {
        progressBar.visibility = ProgressBar.VISIBLE

        CoroutineScope(Dispatchers.Main).launch {
            val result = countriesController.getCountriesByRegion(region)

            progressBar.visibility = ProgressBar.GONE

            when {
                result.isSuccess -> {
                    val countries = result.getOrNull() ?: emptyList()
                    setupRecyclerView(countries)
                }
                result.isFailure -> {
                    Toast.makeText(
                        this@CountriesActivity,
                        "Error al cargar países: ${result.exceptionOrNull()?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun setupRecyclerView(countries: List<Country>) {
        val adapter = CountriesAdapter(countries) { country ->
            val intent = Intent(this, CountryDetailActivity::class.java)
            intent.putExtra("COUNTRY", country)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}