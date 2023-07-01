package com.projetb3.movynov.activities

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.projetb3.movynov.R
import com.projetb3.movynov.activities.adapters.PopularAdapter
import com.projetb3.movynov.databinding.PopularLayoutBinding
import com.projetb3.movynov.dataclasses.MediaMovie
import com.projetb3.movynov.repository.ApiCall
import com.projetb3.movynov.viewmodels.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var mainViewModel : MainViewModel
    private lateinit var drawerLayout : DrawerLayout
    private var movies : List<MediaMovie> = emptyList()
    private lateinit var binding: PopularLayoutBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PopularAdapter

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
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, findViewById(R.id.popular_toolbar), R.string.open_nav, R.string.close_nav)

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

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

    private fun inflateRecycler(movies : List<MediaMovie>){
        val mediaMovieResultsAdapter = PopularAdapter(movies)
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.popular_recycler)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.adapter = mediaMovieResultsAdapter
    }

    private fun fetchPopularMovies() : List<MediaMovie> {
        // Use coroutines to perform the API request asynchronously
        return ApiCall().getPopularMovies()
    }

    public override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}