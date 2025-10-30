package com.example.applista_.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adaptador para mostrar la lista de regiones en un RecyclerView
class RegionsAdapter(
    private val regions: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<RegionsAdapter.RegionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return RegionViewHolder(view)
    }

    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        val region = regions.getOrNull(position)?.takeIf { it.isNotBlank() } ?: "Región no disponible"
        holder.bind(region)
    }

    override fun getItemCount(): Int = regions.size.coerceAtLeast(0)

    inner class RegionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(region: String) {
            textView.text = region
            itemView.setOnClickListener {
                if (region.isNotBlank()) {
                    onItemClick(region)
                }
            }
        }
    }
}
