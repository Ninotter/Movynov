package com.projetb3.movynov.activities.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projetb3.movynov.R
import com.projetb3.movynov.dataclasses.MediaMovie

class PopularAdapter(private val mediaMoviesList: List<MediaMovie>) : RecyclerView.Adapter<PopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mediamovie, parent, false)
        return PopularViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        holder.moviePosterImageView.background = mediaMoviesList[position].posterImage
        holder.movieTitleTextView.text = mediaMoviesList[position].title
        holder.movieLayout.background = mediaMoviesList[position].backdropImage
        holder.movieOverviewTextView.text = mediaMoviesList[position].overview
        holder.movieReleaseDateTextView.text = mediaMoviesList[position].releaseDate
    }

    override fun getItemCount(): Int {
        return mediaMoviesList.size
    }

}