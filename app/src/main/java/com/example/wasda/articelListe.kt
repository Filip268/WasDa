//package com.example.wasda
//
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//
//
//class articelListe : AppCompatActivity() {
//
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter: articelAdapter
//    private val lebensmittelListe = mutableListOf<createArticelActivity.Lebensmittel>()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.view_lebensmittel)
//
//        recyclerView = findViewById(R.id.recyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        adapter = articelAdapter(lebensmittelListe)
//        recyclerView.adapter = adapter
//
//        ladeDaten()
//    }
//
//    private fun ladeDaten() {
//        RetrofitClient.instance.getLebensmittel()
//            .enqueue(object : Callback<List<createArticelActivity.Lebensmittel>> {
//                override fun onResponse(
//                    call: Call<List<createArticelActivity.Lebensmittel>>,
//                    response: Response<List<createArticelActivity.Lebensmittel>>
//                ) {
//                    if (response.isSuccessful) {
//                        val body = response.body()
//                        Toast.makeText(
//                            this@articelListe,
//                            "Erfolg: ${body?.size ?: 0} Items",
//                            Toast.LENGTH_LONG
//                        ).show()
//                        println("DEBUG: Gefundene Items: $body")
//
//                        lebensmittelListe.clear()
//                        lebensmittelListe.addAll(body ?: emptyList())
//                        adapter.notifyDataSetChanged()
//                    } else {
//                        Toast.makeText(
//                            this@articelListe,
//                            "Fehler beim Laden: ${response.code()}",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<List<createArticelActivity.Lebensmittel>>, t: Throwable) {
//                    Toast.makeText(
//                        this@articelListe,
//                        "Fehler: ${t.message}",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            })
//    }
//}
//
