package com.example.wasda

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class CreateKategorieActivity : AppCompatActivity() {

    private val supabase = SupabaseClient.client

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_kategorie) // Das zugehörige Layout!

        val editKategorieName = findViewById<EditText>(R.id.editKategorieName)
        val btnSpeichern = findViewById<Button>(R.id.btnSave)
        val btnAbbrechen = findViewById<Button>(R.id.btnCancel)


        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        btnSpeichern.setOnClickListener {
            val kategorieName = editKategorieName.text.toString().trim()

            if (kategorieName.isEmpty()) {
                Toast.makeText(this, "Bitte Kategorie eingeben", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val kategorie = mapOf("name" to kategorieName)
                    supabase.from("kategorie").insert(kategorie)
                    Toast.makeText(this@CreateKategorieActivity, "Kategorie gespeichert", Toast.LENGTH_SHORT).show()
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@CreateKategorieActivity, "Fehler: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        btnAbbrechen.setOnClickListener {
            finish()
        }
    }
}