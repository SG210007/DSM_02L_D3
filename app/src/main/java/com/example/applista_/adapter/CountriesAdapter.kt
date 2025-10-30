package com.example.applista_.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.applista_.R
import com.example.applista_.model.Country

// Adaptador para mostrar la lista de países en un RecyclerView
class CountriesAdapter(
    private val countries: List<Country>,
    private val onItemClick: (Country) -> Unit
) : RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries.getOrNull(position)
        if (country != null) {
            holder.bind(country)
        }
    }

    override fun getItemCount(): Int = countries.size.coerceAtLeast(0) // Evita valores negativos

    inner class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val flagImageView: ImageView = itemView.findViewById(R.id.flagImageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val capitalTextView: TextView = itemView.findViewById(R.id.capitalTextView)

        fun bind(country: Country) {
            // Validaciones básicas de texto
            nameTextView.text = country.name?.common?.takeIf { it.isNotBlank() } ?: "Nombre no disponible"
            capitalTextView.text = country.capital?.firstOrNull()?.takeIf { it.isNotBlank() } ?: "Capital no disponible"

            // Carga segura de imagen con validación
            val flagUrl = country.flags?.png
            if (!flagUrl.isNullOrBlank()) {
                Glide.with(itemView.context)
                    .load(flagUrl)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_error)
                    .into(flagImageView)
            } else {
                flagImageView.setImageResource(R.drawable.ic_error)
            }

            itemView.setOnClickListener {
                onItemClick(country)
            }
        }
    }
}
