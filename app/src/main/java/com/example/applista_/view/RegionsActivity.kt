package com.example.applista_.view

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applista_.R
import com.example.applista_.adapter.RegionsAdapter
import com.example.applista_.controller.CountriesController

class RegionsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private val countriesController = CountriesController() // controlador de países

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regions)

        initViews()
        setupRecyclerView()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.regionsRecyclerView)
        progressBar = findViewById(R.id.progressBar)

        supportActionBar?.title = "Selecciona una Región" // título
    }

    private fun setupRecyclerView() {
        val regions = countriesController.getRegions() // obtengo lista de regiones
        val adapter = RegionsAdapter(regions) { region ->
            // al hacer click en una región, ir a CountriesActivity
            val intent = Intent(this, CountriesActivity::class.java)
            intent.putExtra("REGION", region)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this) // layout vertical
        recyclerView.adapter = adapter
    }
}
