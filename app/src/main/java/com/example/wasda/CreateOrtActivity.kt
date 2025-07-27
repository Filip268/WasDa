package com.example.wasda

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class CreateOrtActivity : AppCompatActivity() {

    private val supabase = SupabaseClient.client

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_ort)

        val editOrtName = findViewById<EditText>(R.id.editOrtName)
        val btnSpeichern = findViewById<Button>(R.id.btnSave)
        val btnAbbrechen = findViewById<Button>(R.id.btnCancel)

        btnSpeichern.setOnClickListener {
            val ortName = editOrtName.text.toString().trim()

            if (ortName.isEmpty()) {
                Toast.makeText(this, "Bitte Lagerort eingeben", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val ort = mapOf("name" to ortName)
                    supabase.from("ort").insert(ort)
                    Toast.makeText(this@CreateOrtActivity, "Lagerort gespeichert", Toast.LENGTH_SHORT).show()
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@CreateOrtActivity, "Fehler: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        btnAbbrechen.setOnClickListener {
            finish()
        }
    }
}
