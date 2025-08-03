package com.example.wasda

import Artikel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ArtikelAdapter(
    private val artikelListe: MutableList<Artikel>,
    private val onDeleteClick: (Artikel) -> Unit
) : RecyclerView.Adapter<ArtikelAdapter.ArtikelViewHolder>() {

    inner class ArtikelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName: TextView = itemView.findViewById(R.id.ArtikelName)
        val textAblaufdatum: TextView = itemView.findViewById(R.id.Ablaufdatum)
        val textOrt: TextView = itemView.findViewById(R.id.Ort)
        val textKategorie: TextView = itemView.findViewById(R.id.Kategorie)
        val textFach: TextView = itemView.findViewById(R.id.Fach)
        val btnDelete: Button = itemView.findViewById(R.id.btnDeleteArticle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtikelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.article_layout, parent, false)
        return ArtikelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtikelViewHolder, position: Int) {
        val artikel = artikelListe[position]
        holder.textName.text = artikel.name
        holder.textAblaufdatum.text = artikel.ablaufdatum
        holder.textOrt.text = artikel.ort
        holder.textKategorie.text = artikel.kategorie
        holder.textFach.text = artikel.fach

        holder.btnDelete.setOnClickListener {
            onDeleteClick(artikel)
        }
    }

    override fun getItemCount() = artikelListe.size

    fun removeArtikel(artikel: Artikel) {
        val pos = artikelListe.indexOf(artikel)
        if (pos != -1) {
            artikelListe.removeAt(pos)
            notifyItemRemoved(pos)
        }
    }
}


