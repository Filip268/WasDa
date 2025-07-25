//package com.example.wasda
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//class articelAdapter(private val items: List<CreateArticelActivity.>) :
//    RecyclerView.Adapter<articelAdapter.ViewHolder>() {
//
//    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val tvName: TextView = view.findViewById(R.id.tvName)
//        val tvAblaufdatum: TextView = view.findViewById(R.id.tvAblaufdatum)
//        val tvOrt: TextView = view.findViewById(R.id.tvOrt)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.lebensmittel_layout, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = items[position]
//        holder.tvName.text = item.name
//        holder.tvAblaufdatum.text = "Ablaufdatum: ${item.ablaufdatum}"
//        holder.tvOrt.text = "Lagerort: ${item.ort}"
//    }
//
//    override fun getItemCount(): Int = items.size
//}
