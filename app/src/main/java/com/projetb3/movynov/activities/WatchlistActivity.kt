package com.projetb3.movynov.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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


class WatchlistActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watchlist)
        if (viewModel.getConnectedUser() == null){
            Toast.makeText(this, "Vous devez être connecté pour accéder à votre watchlist", Toast.LENGTH_LONG).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val drawerLayout = findViewById<DrawerLayout>(R.id.watchlist_drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.watchlist_nav_view)
        if (viewModel.getConnectedUser() != null){
            MenuBehavior().hideLoginMenu(navigationView, viewModel.getConnectedUser()!!)
        }
        DrawerBehavior().setDrawerOpenOnClick(drawerLayout, navigationView, this, findViewById(R.id.watchlist_toolbar))


        val token = viewModel.getConnectedUser()!!.token!!
        GlobalScope.launch {
            try{
                val watchlist = WatchlistModel().getWatchlistByToken(token)
                var listMovies :List<MediaMovie> = listOf()
                for (watchlistresult in watchlist) {
                    val id = watchlistresult.idMedia!!
                    val movie = MediaMovieModel().getMovieById(id)
                    movie.updatePosterImage()
                    movie.checkIfIsInWatchList(token)
                    listMovies = listMovies + movie
                }
                runOnUiThread(Runnable {
                    inflateRecycler(listMovies, viewModel.getConnectedUser())
                })
            }catch(e:Exception){
                runOnUiThread(Runnable {
                    val errorTextView: TextView = findViewById(R.id.watchlist_error_text)
                    errorTextView.text = "Une erreur est survenue lors de la récupération de votre watchlist"
                    val loadingProgressBar : ProgressBar = findViewById(R.id.progress_bar_watchlist)
                    loadingProgressBar.visibility = ProgressBar.GONE
                    errorTextView.visibility = TextView.VISIBLE
                })
            }
        }
    }

    public fun inflateRecycler(movieList : List<MediaMovie>, user : User? = null){
        val mediaMovieResultsAdapter = MovieListAdapter(movieList.toMutableList(), user, ::navigateToMovieDetails, ::addToWatchList, ::removeFromWatchList)
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.watchlist_recycler)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.adapter = mediaMovieResultsAdapter
        if (movieList.isEmpty()){
            val errorTextView: TextView = findViewById(R.id.watchlist_error_text)
            errorTextView.text = "Vous n'avez aucun film dans votre watchlist"
            val loadingProgressBar : ProgressBar = findViewById(R.id.progress_bar_watchlist)
            loadingProgressBar.visibility = ProgressBar.GONE
            errorTextView.visibility = TextView.VISIBLE
        }else{
            val loadingProgressBar : ProgressBar = findViewById(R.id.progress_bar_watchlist)
            loadingProgressBar.visibility = ProgressBar.GONE
        }
    }

    private fun removeFromWatchList(movie: MediaMovie): Boolean {
        //todo /remove from watchlist
        Toast.makeText(this, "Retiré de votre watchlist", Toast.LENGTH_LONG).show()
        return true
    }


    private fun addToWatchList(movie: MediaMovie) : Boolean {
        //todo /add from watchlist
        Toast.makeText(this, "Ajouté à votre watchlist", Toast.LENGTH_LONG).show()
        return true
    }

    private fun navigateToMovieDetails(id: Int) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return MenuBehavior().onNavigationItemSelected(item, this)

    }
}