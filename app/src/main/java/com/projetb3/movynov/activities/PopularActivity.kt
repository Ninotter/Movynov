package com.projetb3.movynov.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.projetb3.movynov.R
import com.projetb3.movynov.activities.adapters.MovieListAdapter
import com.projetb3.movynov.dataclasses.MediaMovie
import com.projetb3.movynov.dataclasses.auth.User
import com.projetb3.movynov.model.MediaMovieModel
import com.projetb3.movynov.model.WatchlistModel
import com.projetb3.movynov.viewmodels.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PopularActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout : DrawerLayout
    private val viewModel: MainViewModel by viewModels()
    private var movies : List<MediaMovie> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular)
        setSupportActionBar(findViewById(R.id.popular_toolbar))
        supportActionBar?.title = ""

        /**
         * Navigation Drawer
         */
        drawerLayout = findViewById(R.id.popular_drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.popular_nav_view)
        if (viewModel.getConnectedUser() != null){
            MenuBehavior().hideLoginMenu(navigationView, viewModel.getConnectedUser()!!)
        }else{
            MenuBehavior().menuForNoUser(navigationView)
        }
        DrawerBehavior().setDrawerOpenOnClick(drawerLayout, navigationView, this, findViewById(R.id.popular_toolbar))

        GlobalScope.launch {
            movies = fetchPopularMovies()
            for (mediaMovie in movies) {
                mediaMovie.updatePosterImage()
                if (viewModel.getConnectedUser() != null){
                    mediaMovie.checkIfIsInWatchList(viewModel.getConnectedUser()?.token)
                }
            }
            runOnUiThread(Runnable {
                inflateRecycler(movies, viewModel.getConnectedUser())
                val progressBar = findViewById<ProgressBar>(R.id.progress_bar_popular)
                progressBar.visibility = ProgressBar.GONE
            })
        }
    }

    override fun onResume() {
        super.onResume()
        //Checks again watchlist status.
        GlobalScope.launch {
            if (viewModel.getConnectedUser() != null){
                for(mediaMovie in movies) {
                    mediaMovie.checkIfIsInWatchList(viewModel.getConnectedUser()?.token)
                }
                runOnUiThread(Runnable {
                    inflateRecycler(movies, viewModel.getConnectedUser())
                })
            }
        }
    }


    private fun inflateRecycler(movies : List<MediaMovie>, user: User? = null){
        val mediaMovieResultsAdapter = MovieListAdapter(movies.toMutableList(), user, ::navigateToMovieDetails, ::addToWatchList, ::removeFromWatchList)
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.popular_recycler)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.adapter = mediaMovieResultsAdapter
    }


    private fun fetchPopularMovies() : List<MediaMovie> {
        return MediaMovieModel().getPopularMovies()
    }

    public override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return MenuBehavior().onNavigationItemSelected(item, this)
    }

    public fun addToWatchList(movie : MediaMovie): Boolean{
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

    public fun navigateToMovieDetails(id : Int){
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}