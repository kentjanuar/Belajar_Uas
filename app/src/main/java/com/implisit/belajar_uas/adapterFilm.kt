package com.implisit.belajar_uas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adapterFilm(private val listFilm: MutableList<film>) : RecyclerView.Adapter<adapterFilm.ListViewHolder>() {

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _judul: TextView = itemView.findViewById(R.id.judul)
        var _deskripsi: TextView = itemView.findViewById(R.id.deskripsi)
        var _gambar: ImageView = itemView.findViewById(R.id.poster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.film, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val film = listFilm[position]
        holder._judul.text = film.judul
        holder._deskripsi.text = film.deskripsi
        val resourceId = holder.itemView.context.resources.getIdentifier(
            film.poster,
            "drawable",
            holder.itemView.context.packageName
        )
        if (resourceId != 0) {
            holder._gambar.setImageResource(resourceId)
        } else {
            holder._gambar.setImageResource(R.drawable.error)
        }

    }

    override fun getItemCount(): Int {
        return listFilm.size
    }

}