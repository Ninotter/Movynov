package com.projetb3.movynov.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.projetb3.movynov.R
import com.projetb3.movynov.activities.adapters.PopularAdapter
import com.projetb3.movynov.dataclasses.MediaMovie
import com.projetb3.movynov.dataclasses.auth.User
import com.projetb3.movynov.model.MediaMovieModel
import com.projetb3.movynov.viewmodels.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PopularActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout : DrawerLayout
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.popular_layout)
        setSupportActionBar(findViewById(R.id.popular_toolbar))
        supportActionBar?.title = ""

        /**
         * Navigation Drawer
         */
        drawerLayout = findViewById(R.id.popular_drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.popular_nav_view)
        if (viewModel.getConnectedUser() != null){
            hideLoginMenu(navigationView, viewModel.getConnectedUser()!!)
        }
        DrawerBehavior().setDrawerOpenOnClick(drawerLayout, navigationView, this, findViewById(R.id.popular_toolbar))

        GlobalScope.launch {
            val movies = fetchPopularMovies()
            for (mediaMovie in movies) {
                mediaMovie.updatePosterImage()
            }
            runOnUiThread(Runnable {
                inflateRecycler(movies)
            })
        }
    }

    private fun hideLoginMenu(navigationView: NavigationView,connectedUser: User) {
        navigationView.menu.findItem(R.id.nav_login).isVisible = false
        navigationView.menu.findItem(R.id.nav_logout).isVisible = true
        navigationView.menu.findItem(R.id.nav_watchlist).isVisible = true
        navigationView.menu.findItem(R.id.nav_forum).isVisible = true
        navigationView.menu.findItem(R.id.nav_username).isVisible = true
        //todo change to username
        navigationView.menu.findItem(R.id.nav_username).title = connectedUser.email
    }


    private fun inflateRecycler(movies : List<MediaMovie>){
        val mediaMovieResultsAdapter = PopularAdapter(movies, ::navigateToMovieDetails, ::addToWatchList)
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

    public fun addToWatchList(movie : MediaMovie){
        //TODO
        Toast.makeText(this, "Added to watchlist", Toast.LENGTH_SHORT).show()
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