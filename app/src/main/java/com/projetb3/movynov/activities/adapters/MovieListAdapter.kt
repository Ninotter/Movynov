package com.projetb3.movynov.activities.adapters

import android.provider.Settings.Global
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.projetb3.movynov.R
import com.projetb3.movynov.dataclasses.MediaMovie
import com.projetb3.movynov.dataclasses.auth.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieListAdapter(
    private var mediaMoviesList: MutableList<MediaMovie>,
    private val user : User?,
    private val onItemClick: (id : Int) -> Unit,
    private val onAddToWatchListClick: (movie: MediaMovie) -> Boolean,
    private val onRemoveFromWatchListClick: (movie: MediaMovie) -> Boolean
                    ) : RecyclerView.Adapter<MovieListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mediamovie, parent, false)
        return MovieListViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.moviePosterImageView.background = mediaMoviesList[position].posterImage
        holder.movieTitleTextView.text = mediaMoviesList[position].title
        holder.movieOverviewTextView.text = mediaMoviesList[position].overview
        var yearReleaseDate : String = ""
        if(mediaMoviesList[position].releaseDate!!.length < 4){
            yearReleaseDate = mediaMoviesList[position].releaseDate!!
        }else{
            yearReleaseDate = mediaMoviesList[position].releaseDate?.substring(0, 4)!!
        }
        holder.movieReleaseDateTextView.text = yearReleaseDate
        holder.movieRatingTextView.text = mediaMoviesList[position].voteAverage.toString() + "\uD83C\uDF1F"
        holder.addToWatchListButton.setOnClickListener {
            onAddToWatchListClick(mediaMoviesList[position])
        }
        holder.movieLayout.setOnClickListener {
            onItemClick(mediaMoviesList[position].id!!)
        }

        when(mediaMoviesList[position].isInWatchList!! ){
            MediaMovie.IsInWatchList.TRUE ->{
                holder.removeFromWatchListButton.visibility = View.VISIBLE
                holder.removeFromWatchListButton.setOnClickListener {
                    //implement check if possible
                    GlobalScope.launch {
                        onRemoveFromWatchListClick(mediaMoviesList[position])
                    }
                    onRemoveFromWatchList(holder.addToWatchListButton,holder.removeFromWatchListButton,position)
                }
            }
            MediaMovie.IsInWatchList.FALSE -> {
                holder.addToWatchListButton.visibility = View.VISIBLE
                holder.addToWatchListButton.setOnClickListener {
                    GlobalScope.launch{
                        onAddToWatchListClick(mediaMoviesList[position])
                    }
                    onAddToWatchList(holder.addToWatchListButton,holder.removeFromWatchListButton,position)
                }
            }
            MediaMovie.IsInWatchList.USER_NOT_LOGGED_IN -> {
                holder.removeFromWatchListButton.visibility = View.GONE
                holder.addToWatchListButton.visibility = View.GONE
            }
        }
    }

    private fun onAddToWatchList(buttonAdd : ImageView, buttonRemove : ImageView, position: Int){
        buttonAdd.visibility = View.GONE
        buttonRemove.visibility = View.VISIBLE
        mediaMoviesList[position].isInWatchList = MediaMovie.IsInWatchList.TRUE
        buttonAdd.setOnClickListener(null)
        buttonRemove.setOnClickListener {
            GlobalScope.launch {
                onRemoveFromWatchListClick(mediaMoviesList[position])
            }
            onRemoveFromWatchList(buttonAdd, buttonRemove, position)
        }
    }

    private fun onRemoveFromWatchList(buttonAdd : ImageView, buttonRemove : ImageView, position: Int){
        buttonAdd.visibility = View.VISIBLE
        buttonRemove.visibility = View.GONE
        mediaMoviesList[position].isInWatchList = MediaMovie.IsInWatchList.FALSE
        buttonRemove.setOnClickListener(null)
        buttonAdd.setOnClickListener {
            GlobalScope.launch {
                onAddToWatchListClick(mediaMoviesList[position])

            }
            onAddToWatchList(buttonAdd, buttonRemove, position)
        }
    }

    override fun getItemCount(): Int {
        return mediaMoviesList.size
    }

    //fun onRemoveFromWatchlist(position: Int){
        //mediaMoviesList.remove(mediaMoviesList[position])
        //notifyItemRemoved(position)
        //notifyItemRangeChanged(position, mediaMoviesList.size - position)
    //}

}