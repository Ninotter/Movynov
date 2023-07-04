package com.projetb3.movynov.activities.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.projetb3.movynov.R

class TrailersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val trailerText : TextView = itemView.findViewById(R.id.item_trailer_text)
}