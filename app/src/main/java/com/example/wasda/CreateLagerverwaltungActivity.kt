package com.example.wasda

import Fach
import Ort
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

class CreateLagerverwaltungActivity : AppCompatActivity() {
    private val supabase = SupabaseClient.client

    private lateinit var spinnerOrt: Spinner
    private lateinit var spinnerFach: Spinner
    private lateinit var btnVerknuepfen: Button
    private lateinit var btnCancel: Button

    private val ortList = mutableListOf<Pair<Int, String>>()
    private val fachList = mutableListOf<Pair<Int, String>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_lagerverwaltung)

        spinnerOrt = findViewById(R.id.spinnerOrt)
        spinnerFach = findViewById(R.id.spinnerFach)
        btnVerknuepfen = findViewById(R.id.btnVerknuepfen)
        btnCancel = findViewById(R.id.btnCancel)

        loadOrte()
        loadFaecher()

        btnVerknuepfen.setOnClickListener {
            val selectedOrtId = ortList.getOrNull(spinnerOrt.selectedItemPosition)?.first
            val selectedFachId = fachList.getOrNull(spinnerFach.selectedItemPosition)?.first

            if (selectedOrtId != null && selectedFachId != null) {
                verknuepfeOrtMitFach(selectedOrtId, selectedFachId)
            } else {
                Toast.makeText(this, "Bitte Ort und Fach auswählen", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancel.setOnClickListener {
            finish()
        }

        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            finish()
        }
    }

    private fun loadOrte() {
        lifecycleScope.launch {
            try {
                val response = supabase.postgrest["ort"].select().decodeList<Ort>()
                Log.d("SupabaseResponse", "Orte geladen: $response")

                ortList.clear()
                ortList.addAll(response.filter { it.id != null }.map { it.id!! to it.name })

                val adapter = ArrayAdapter(this@CreateLagerverwaltungActivity, android.R.layout.simple_spinner_item, ortList.map { it.second })
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerOrt.adapter = adapter
            } catch (e: Exception) {
                Log.e("SupabaseError", "Fehler beim Laden der Orte", e)
                Toast.makeText(this@CreateLagerverwaltungActivity, "Fehler beim Laden der Orte: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }



    private fun loadFaecher() {
        lifecycleScope.launch {
            try {
                val response = supabase.postgrest["fach"].select().decodeList<Fach>()
                fachList.clear()
                fachList.addAll(response.map { it.id to it.fach_bezeichnung })
                val adapter = ArrayAdapter(this@CreateLagerverwaltungActivity, android.R.layout.simple_spinner_item, fachList.map { it.second })
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerFach.adapter = adapter
            } catch (e: Exception) {
                Toast.makeText(this@CreateLagerverwaltungActivity, "Fehler beim Laden der Fächer", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun verknuepfeOrtMitFach(ortId: Int, fachId: Int) {
        lifecycleScope.launch {
            try {
                val payload = mapOf("ort_id" to ortId, "fach_id" to fachId)
                supabase.postgrest["ort_fach"].insert(payload)
                Toast.makeText(this@CreateLagerverwaltungActivity, "Verknüpfung erfolgreich!", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@CreateLagerverwaltungActivity, "Fehler bei der Verknüpfung", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
