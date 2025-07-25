package com.example.wasda

import Artikel
import ArtikelInsert
import Fach
import Kategorie
import Ort
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.int
import java.util.*
import kotlinx.serialization.json.jsonPrimitive

class CreateArtikelActivity : AppCompatActivity() {

    private val supabase = SupabaseClient.client

    private lateinit var spinnerKategorie: Spinner
    private lateinit var spinnerOrt: Spinner
    private lateinit var spinnerFach: Spinner

    private var kategorien: List<Kategorie> = emptyList()
    private var orte: List<Ort> = emptyList()
    private var fächer: List<Fach> = emptyList()

    private var gewählteKategorieId: String? = null
    private var gewählterOrtId: String? = null
    private var gewählterFachId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item)


        val editName = findViewById<EditText>(R.id.editName)
        val editDate = findViewById<EditText>(R.id.editDate)
        spinnerOrt = findViewById(R.id.spinnerLagerOrt)
        spinnerFach = findViewById(R.id.spinnerFach)
        spinnerKategorie = findViewById(R.id.spinnerKategorie)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnCancel = findViewById<Button>(R.id.btnCancel)

        editDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val selectedDate = "%04d-%02d-%02d".format(year, month + 1, dayOfMonth)
                    editDate.setText(selectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }


        ladeDaten()

        btnSave.setOnClickListener {
            val name = editName.text.toString().trim()
            val datum = editDate.text.toString().trim()

            if (name.isEmpty() || datum.isEmpty() ||
                gewählteKategorieId == null || gewählterOrtId == null || gewählterFachId == null) {
                Toast.makeText(this, "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val artikel = ArtikelInsert(name, datum, gewählteKategorieId!!.toInt())

                    val inserted = supabase.from("artikel").insert(artikel) {
                    }.decodeSingle<JsonObject>()
                    val artikelId = inserted["id"]?.jsonPrimitive?.int

                    if (artikelId != null) {
                        supabase.from("ort_fach").insert(
                            mapOf(
                                "artikel_id" to artikelId,
                                "ort_id" to gewählterOrtId!!,
                                "fach_id" to gewählterFachId!!
                            )
                        )
                        Toast.makeText(this@CreateArtikelActivity, "Gespeichert!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@CreateArtikelActivity, "Fehler: Artikel-ID fehlt!", Toast.LENGTH_LONG).show()
                    }

                } catch (e: Exception) {
                    Toast.makeText(this@CreateArtikelActivity, "Fehler: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }

    // 🔽 Jetzt außerhalb von onCreate!
    private fun ladeDaten() {
        lifecycleScope.launch {
            try {
                kategorien = supabase.from("kategorie").select().decodeList()
                orte = supabase.from("ort").select().decodeList()
                fächer = supabase.from("fach").select().decodeList()

                spinnerKategorie.adapter = ArrayAdapter(
                    this@CreateArtikelActivity,
                    android.R.layout.simple_spinner_item,
                    kategorien.map { it.kategorie }
                )
                spinnerOrt.adapter = ArrayAdapter(
                    this@CreateArtikelActivity,
                    android.R.layout.simple_spinner_item,
                    orte.map { it.name }
                )
                spinnerFach.adapter = ArrayAdapter(
                    this@CreateArtikelActivity,
                    android.R.layout.simple_spinner_item,
                    fächer.map { it.fach_bezeichnung }
                )

                spinnerKategorie.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                        gewählteKategorieId = kategorien[pos].id.toString()
                    }
                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }

                spinnerOrt.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                        gewählterOrtId = orte[pos].id.toString()
                    }
                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }

                spinnerFach.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                        gewählterFachId = fächer[pos].id.toString()
                    }
                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }

            } catch (e: Exception) {
                Toast.makeText(this@CreateArtikelActivity, "Fehler beim Laden: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
