package com.projetb3.movynov.activities.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.projetb3.movynov.R

class PopularViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    val movieLayout : LinearLayoutCompat = itemView.findViewById(R.id.movie_item)
    val movieTitleTextView : TextView = itemView.findViewById(R.id.movie_title)
    val moviePosterImageView : ImageView = itemView.findViewById(R.id.movie_poster)
}
