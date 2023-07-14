package com.projetb3.movynov.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.projetb3.movynov.R
import com.projetb3.movynov.activities.adapters.MovieListAdapter
import com.projetb3.movynov.dataclasses.MediaMovie
import com.projetb3.movynov.dataclasses.auth.User
import com.projetb3.movynov.model.MediaMovieModel
import com.projetb3.movynov.model.WatchlistModel
import com.projetb3.movynov.viewmodels.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchResultsActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private var mediaMovieList : List<MediaMovie> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        val search = intent.getStringExtra("query")

        GlobalScope.launch {
            mediaMovieList = MediaMovieModel().getMoviesByTitle(search!!)
            for (mediaMovie in mediaMovieList){
                mediaMovie.updatePosterImage()
                if (viewModel.getConnectedUser() != null){
                    mediaMovie.checkIfIsInWatchList(viewModel.getConnectedUser()?.token)
                }
            }
            runOnUiThread(Runnable {
                inflateRecycler(mediaMovieList)
                val progressBar = findViewById<ProgressBar>(R.id.progress_bar_search_results)
                progressBar.visibility = ProgressBar.GONE
            })
        }
    }

    private fun inflateRecycler(mediaMovieList: List<MediaMovie>, user : User? = null) {
        val mediaMovieResultsAdapter = MovieListAdapter(mediaMovieList.toMutableList(), user, ::navigateToMovieDetails, ::addToWatchList, ::removeFromWatchList)
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.search_results_recycler_view)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.adapter = mediaMovieResultsAdapter
        if(mediaMovieList.isEmpty()){
            val errorTextView = findViewById<TextView>(R.id.search_results_error_text)
            errorTextView.text = getString(R.string.no_movies_found_by_search_error)
            errorTextView.visibility = TextView.VISIBLE
        }
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

    private fun addToWatchList(movie: MediaMovie): Boolean {
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

    private fun navigateToMovieDetails(idMovie: Int) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("id", idMovie)
        startActivity(intent)
    }

    override fun onBackPressed() {
        finish()
    }
}