package com.example.wasda

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnNewItem = findViewById<Button>(R.id.btnNewItem)
        val btnCreateOrt = findViewById<Button>(R.id.btnCreateOrt)
        val btnCreateFach = findViewById<Button>(R.id.btnCreateFach)
        val btnCreateKategorie = findViewById<Button>(R.id.btnCreateKategorie)
//        val btnVerknuepfen = findViewById<Button>(R.id.btnVerknuepfen)

        btnNewItem.setOnClickListener {
            startActivity(Intent(this, CreateArtikelActivity::class.java))
        }

        btnCreateOrt.setOnClickListener {
            startActivity(Intent(this, CreateOrtActivity::class.java))
        }

        btnCreateFach.setOnClickListener {
            startActivity(Intent(this, CreateFachActivity::class.java))
        }

        btnCreateKategorie.setOnClickListener {
            startActivity(Intent(this, CreateKategorieActivity::class.java))
        }

//        btnCreateLagerverwaltung.setOnClickListener {
//            startActivity(Intent(this, CreateLagerverwaltungActivity::class.java))
//        }
    }
}
