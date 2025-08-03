package com.example.wasda

import Artikel
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class ViewArticlesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var artikelAdapter: ArtikelAdapter
    private val artikelListe = mutableListOf<Artikel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_articles)

        recyclerView = findViewById(R.id.recyclerViewArticles)
        recyclerView.layoutManager = LinearLayoutManager(this)

        artikelAdapter = ArtikelAdapter(artikelListe) { artikel ->
            deleteArtikel(artikel)
        }
        recyclerView.adapter = artikelAdapter

        lifecycleScope.launch {
            val geladen = ladeArtikel()
            artikelListe.addAll(geladen)
            artikelAdapter.notifyDataSetChanged()
        }
    }

    private suspend fun ladeArtikel(): List<Artikel> {
        val supabase = SupabaseClient.client
        return supabase
            .from("artikel_details")
            .select()
            .decodeList<Artikel>()
    }

    private fun deleteArtikel(artikel: Artikel) {
        lifecycleScope.launch {
            try {
                val supabase = SupabaseClient.client
                supabase
                    .from("artikel")
                    .delete {
                        filter {
                            eq("id", artikel.id)
                        }
                    }

                artikelAdapter.removeArtikel(artikel)
                Toast.makeText(this@ViewArticlesActivity, "Artikel gelöscht", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(
                    this@ViewArticlesActivity,
                    "Fehler beim Löschen: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
