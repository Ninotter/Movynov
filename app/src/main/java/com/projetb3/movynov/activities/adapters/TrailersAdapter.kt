package com.projetb3.movynov.activities.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projetb3.movynov.R
import com.projetb3.movynov.dataclasses.videos.VideoResults


class TrailersAdapter(private val listOfVideosResults : List<VideoResults>, private val onItemClick: (url: String) -> Unit) : RecyclerView.Adapter<TrailersViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trailer, parent, false)
        return TrailersViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrailersViewHolder, position: Int) {
        val videoResult = listOfVideosResults[position]
        if (
            videoResult.site == "YouTube"
        ){
            holder.trailerText.text = videoResult.name
            //underline
            holder.trailerText.setPaintFlags(holder.trailerText.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
            holder.trailerText.setOnClickListener {
                onItemClick("https://www.youtube.com/watch?v=${videoResult.key}")
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfVideosResults.size
    }
}