package com.example.wasda

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class CreateFachActivity : AppCompatActivity() {

    private val supabase = SupabaseClient.client

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_fach) // Das zugehörige Layout!

        val editFachName = findViewById<EditText>(R.id.editFachName)
        val btnSpeichern = findViewById<Button>(R.id.btnSave)
        val btnAbbrechen = findViewById<Button>(R.id.btnCancel)

        btnSpeichern.setOnClickListener {
            val fachName = editFachName.text.toString().trim()

            if (fachName.isEmpty()) {
                Toast.makeText(this, "Bitte Fach eingeben", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val fach = mapOf("name" to fachName)
                    supabase.from("fach").insert(fach)
                    Toast.makeText(this@CreateFachActivity, "Fach gespeichert", Toast.LENGTH_SHORT).show()
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@CreateFachActivity, "Fehler: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        btnAbbrechen.setOnClickListener {
            finish()
        }
    }
}