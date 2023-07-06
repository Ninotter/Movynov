package com.projetb3.movynov.activities.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projetb3.movynov.R
import com.projetb3.movynov.dataclasses.MediaMovie

class MovieListAdapter(
    private val mediaMoviesList: List<MediaMovie>,
    private val onItemClick: (id : Int) -> Unit,
    private val onAddToWatchListClick: (movie: MediaMovie) -> Unit
                    ) : RecyclerView.Adapter<MovieListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mediamovie, parent, false)
        return MovieListViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.moviePosterImageView.background = mediaMoviesList[position].posterImage
        holder.movieTitleTextView.text = mediaMoviesList[position].title
        holder.movieOverviewTextView.text = mediaMoviesList[position].overview
        if(mediaMoviesList[position].releaseDate!!.length < 4){
            val yearReleaseDate = mediaMoviesList[position].releaseDate
        }else{
            val yearReleaseDate = mediaMoviesList[position].releaseDate?.substring(0, 4)
        }
        holder.movieRatingTextView.text = mediaMoviesList[position].voteAverage.toString() + "\uD83C\uDF1F"
        holder.addToWatchListButton.setOnClickListener {
            onAddToWatchListClick(mediaMoviesList[position])
        }
        holder.movieLayout.setOnClickListener {
            onItemClick(mediaMoviesList[position].id!!)
        }
    }

    override fun getItemCount(): Int {
        return mediaMoviesList.size
    }

}