package com.example.applista_.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.applista_.R
import com.example.applista_.controller.CountriesController

class MainActivity : AppCompatActivity() {

    private val countriesController = CountriesController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regions) // Usa el layout de regions

        // Ir directamente a RegionsActivity
        val intent = Intent(this, RegionsActivity::class.java)
        startActivity(intent)
        finish() // Cierra MainActivity
    }
}