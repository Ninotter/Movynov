package com.projetb3.movynov.activities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.projetb3.movynov.R
import com.projetb3.movynov.activities.adapters.WatchProvidersAdapter
import com.projetb3.movynov.dataclasses.MediaMovie
import com.projetb3.movynov.dataclasses.watchproviders.Flatrate
import com.projetb3.movynov.repository.ApiCall
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.internal.toHexString
import java.lang.Exception


class MovieDetailActivity() : AppCompatActivity() {
    private lateinit var movie: MediaMovie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_layout)
        val intent = intent
        val idMovie = intent.getIntExtra("id", 1)

        GlobalScope.launch {
            movie = fetchMovieDetails(idMovie)
            movie.updatePosterImageFullResolution()
            movie.updateBackDropImageFullResolution()
            //setting up watch providers for recyclerview
            for(flatrate: Flatrate in movie.watchProviders?.results?.FR?.flatrate!!){
                if (flatrate.logoPath != null){
                    flatrate.loadLogoImage()
                }
            }

            runOnUiThread(Runnable {
                //Inflate data...
                inflateData()
                findViewById<ProgressBar>(R.id.progress_bar_details).visibility = View.GONE;
                findViewById<ConstraintLayout>(R.id.details_layout_without_progressbar).visibility = View.VISIBLE
            })
        }
    }


    private fun inflateData(){
        findViewById<TextView>(R.id.details_movie_title).text = movie.title
        findViewById<TextView>(R.id.details_movie_vote_average).text = movie.voteAverage.toString() + "\uD83C\uDF1F"
        //Sets the color of the rating depending on the value
        if (movie.voteAverage != null){
            val color = Color.rgb(255 - (movie.voteAverage!! * 255 / 10).toInt(), (movie.voteAverage!! * 255 / 10).toInt(), 0)
            findViewById<TextView>(R.id.details_movie_vote_average).setTextColor(color)
            findViewById<TextView>(R.id.details_user_score_label).setTextColor(color)
        }
        //change text of rating color mapping red to green
        findViewById<ImageView>(R.id.details_movie_backdrop).background = movie.backdropImage
        findViewById<ImageView>(R.id.details_movie_backdrop).alpha = 0.8f
        findViewById<ImageView>(R.id.details_movie_poster).background = movie.posterImage
        try{
            val year = movie.releaseDate!!.substring(0, 4)
            val hours = movie.runtime!! / 60
            val minutes = movie.runtime!! % 60
            findViewById<TextView>(R.id.details_movie_release_date_and_duration).text = "$year | $hours h $minutes min"
        }catch(e:Exception){
            findViewById<TextView>(R.id.details_movie_release_date_and_duration).isVisible = false
        }
        try{
            var genres = ""
            for (genre in movie.genres!!){
                genres += genre.name + ", "
            }
            findViewById<TextView>(R.id.details_movie_genres).text = genres.substring(0, genres.length - 2)
        }catch(e:Exception){
            findViewById<TextView>(R.id.details_movie_genres).isVisible = false
        }
        if (movie.watchProviders?.results?.FR?.flatrate != null){
            inflateRecycler(movie.watchProviders?.results?.FR?.flatrate!!)
        }else{
            findViewById<TextView>(R.id.popular_recycler).visibility = View.GONE
        }
    }

    private fun inflateRecycler(flatrates : List<Flatrate>){
        val watchProvidersAdapter = WatchProvidersAdapter(flatrates)
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.details_movie_platforms_recyclerview)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = watchProvidersAdapter
    }

    private fun fetchMovieDetails(idMovie: Int): MediaMovie {
        return ApiCall().getMovieAndWatchProvidersById(idMovie)
    }

    override fun onBackPressed() {
        //todo implement navigation, go back to previous activity
    }
}