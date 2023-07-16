package com.projetb3.movynov.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projetb3.movynov.R
import com.projetb3.movynov.activities.adapters.CreditsAdapter
import com.projetb3.movynov.activities.adapters.MovieListAdapter
import com.projetb3.movynov.activities.adapters.TrailersAdapter
import com.projetb3.movynov.activities.adapters.WatchProvidersAdapter
import com.projetb3.movynov.dataclasses.MediaMovie
import com.projetb3.movynov.dataclasses.auth.User
import com.projetb3.movynov.dataclasses.credits.Cast
import com.projetb3.movynov.dataclasses.videos.VideoResults
import com.projetb3.movynov.dataclasses.watchproviders.Flatrate
import com.projetb3.movynov.model.MediaMovieModel
import com.projetb3.movynov.model.WatchlistModel
import com.projetb3.movynov.viewmodels.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MovieDetailActivity() : AppCompatActivity() {
    private lateinit var movie: MediaMovie
    private lateinit var recommandations : List<MediaMovie>
    private lateinit var lastActivity : AppCompatActivity
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        val intent = intent
        val idMovie = intent.getIntExtra("id", 1)

        GlobalScope.launch {
            movie = fetchMovieDetails(idMovie)
            recommandations = fetchRecommendations(idMovie)
            //downloads image from path
            movie.updatePosterImageFullResolution()
            movie.updateBackDropImageFullResolution()
            for (mediaMovie in recommandations) {
                mediaMovie.updatePosterImage()
                mediaMovie.checkIfIsInWatchList(viewModel.getConnectedUser()?.token)
            }
            //setting up watch providers for recyclerview
            for(flatrate: Flatrate in movie.watchProviders?.results?.FR?.flatrate!!){
                if (flatrate.logoPath != null){
                    flatrate.loadLogoImage()
                }
            }
            for(cast: Cast in movie.credits?.cast!!){
                if (cast.profilePath != null){
                    cast.loadProfileImage()
                }
                else{
                    cast.profileImage = resources.getDrawable(R.drawable.no_actor_picture, null)
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

    private fun fetchRecommendations(idMovie: Int): List<MediaMovie> {
        return MediaMovieModel().getRelatedMovies(idMovie)
    }


    private fun inflateData(){
        //Movie Title
        findViewById<TextView>(R.id.details_movie_title).text = movie.title
        //Movie vote average
        findViewById<TextView>(R.id.details_movie_vote_average).text = movie.voteAverage.toString() + "\uD83C\uDF1F"
        //Sets the color of the rating depending on the value
        if (movie.voteAverage != null){
            val color = Color.rgb(255 - (movie.voteAverage!! * 255 / 10).toInt(), (movie.voteAverage!! * 255 / 10).toInt(), 0)
            findViewById<TextView>(R.id.details_movie_vote_average).setTextColor(color)
            findViewById<TextView>(R.id.details_user_score_label).setTextColor(color)
        }
        //backdrop and poster image
        findViewById<ImageView>(R.id.details_movie_backdrop).background = movie.backdropImage
        findViewById<ImageView>(R.id.details_movie_backdrop).alpha = 0.6f
        findViewById<ImageView>(R.id.details_movie_poster).background = movie.posterImage
        //Movie tagline
        if (movie.tagline != ""){
            findViewById<TextView>(R.id.details_movie_tagline).text = "-"+ movie.tagline
            //underline tagline
            findViewById<TextView>(R.id.details_movie_tagline).paintFlags = findViewById<TextView>(R.id.details_movie_tagline).paintFlags or Paint.UNDERLINE_TEXT_FLAG
        }else{
            findViewById<TextView>(R.id.details_movie_tagline).visibility = View.GONE
        }
        //Movie overview
        findViewById<TextView>(R.id.details_movie_overview).text = movie.overview
        //Movie release date and duration
        try{
            val year = movie.releaseDate!!.substring(0, 4)
            val hours = movie.runtime!! / 60
            val minutes = movie.runtime!! % 60
            findViewById<TextView>(R.id.details_movie_release_date_and_duration).text = "$year | $hours h $minutes min"
        }catch(e:Exception){
            findViewById<TextView>(R.id.details_movie_release_date_and_duration).visibility = View.GONE
        }
        //Movie genres
        try{
            var genres = ""
            for (genre in movie.genres!!){
                genres += genre.name + ", "
            }
            findViewById<TextView>(R.id.details_movie_genres).text = genres.substring(0, genres.length - 2)
        }catch(e:Exception){
            findViewById<TextView>(R.id.details_movie_genres).isVisible = false
        }
        //Movie platforms
        if (movie.watchProviders?.results?.FR?.flatrate != null && movie.watchProviders?.results?.FR?.flatrate!!.isNotEmpty()){
            inflateWatchProvidersRecycler(movie.watchProviders?.results?.FR?.flatrate!!)
        }else{
            findViewById<RecyclerView>(R.id.details_movie_platforms_recyclerview).visibility = View.GONE
            findViewById<TextView>(R.id.details_movie_platforms_text).visibility = View.GONE
        }
        //Movie cast
        if (movie.credits?.cast != null && movie.credits?.cast!!.isNotEmpty()){
            inflateCreditsRecycler(movie.credits?.cast!!)
        }else{
            findViewById<RecyclerView>(R.id.details_movie_platforms_recyclerview).visibility = View.GONE
            findViewById<TextView>(R.id.details_movie_platforms_text).visibility = View.GONE
        }
        //Movie recommandations
        inflateRecommandationsRecycler(recommandations, viewModel.getConnectedUser())

        //Movie trailers link list
        InflateTrailerRecycler(movie.videos?.results!!)

        //Movie forums
        findViewById<Button>(R.id.details_movies_button_forum).setOnClickListener {
            val intent = Intent(this, ForumActivity::class.java)
            intent.putExtra("idMovie", movie.id)
            startActivity(intent)
        }
    }

    private fun inflateWatchProvidersRecycler(flatrates : List<Flatrate>){
        val watchProvidersAdapter = WatchProvidersAdapter(flatrates)
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.details_movie_platforms_recyclerview)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = watchProvidersAdapter
    }

    private fun inflateCreditsRecycler(cast : List<Cast>){
        val creditsAdapter = CreditsAdapter(cast)
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.details_movie_cast_recyclerview)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = creditsAdapter
    }

    private fun inflateRecommandationsRecycler(movies : List<MediaMovie>, user : User? = null){
        val recommandationsAdapter = MovieListAdapter(movies.toMutableList(), user, ::navigateToMovieDetails, ::addToWatchList, ::removeFromWatchList)
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.details_movie_recommandations_recyclerview)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = recommandationsAdapter
    }

    private fun removeFromWatchList(movie: MediaMovie): Boolean {
        if (WatchlistModel().removeFromWatchlist(viewModel.getConnectedUser()?.token!!, movie.id!!))
        {
            runOnUiThread(Runnable {
                Toast.makeText(this, "Removed from watchlist", Toast.LENGTH_SHORT).show()
            })
            return true
        }
        runOnUiThread(Runnable {Toast.makeText(this, "Error while removing from watchlist", Toast.LENGTH_SHORT).show()})
        return true
    }

    private fun InflateTrailerRecycler(trailers : List<VideoResults>){
        val trailersAdapter = TrailersAdapter(trailers, ::navigateToTrailer)
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.details_movie_trailers_recyclerview)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = trailersAdapter
    }

    private fun navigateToTrailer(url: String) {
        val intent = Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url))
        intent.putExtra("url", url)
        startActivity(intent)
    }

    private fun addToWatchList(movie : MediaMovie) : Boolean{
        if(WatchlistModel().addToWatchList(viewModel.getConnectedUser()?.token!!, movie.id!!)){
            runOnUiThread(Runnable {
                Toast.makeText(this, "Added to watchlist", Toast.LENGTH_SHORT).show()
            })
            return true;
        }
        runOnUiThread(Runnable {
            Toast.makeText(this, "Error while adding to watchlist", Toast.LENGTH_SHORT).show()
        })
        return false;
    }

    private fun navigateToMovieDetails(id : Int){
        //TODO
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    private fun fetchMovieDetails(idMovie: Int): MediaMovie {
        return MediaMovieModel().getMovieById(idMovie)
    }

    override fun onBackPressed() {
        finish()
    }
}