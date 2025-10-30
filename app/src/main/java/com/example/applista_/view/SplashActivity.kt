package com.example.applista_.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.applista_.R
import kotlinx.coroutines.*
import android.widget.ImageView

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // animar icono
        val icon = findViewById<ImageView>(R.id.iconImageView)
        icon.animate().alpha(1f).setDuration(1500).start()

        // espera 3.5 segundos y abre MainActivity
        CoroutineScope(Dispatchers.Main).launch {
            delay(3500)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish() // cerrar splash
        }
    }
}
