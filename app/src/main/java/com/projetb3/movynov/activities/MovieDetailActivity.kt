package com.projetb3.movynov.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.projetb3.movynov.R
import com.projetb3.movynov.dataclasses.MediaMovie
import com.projetb3.movynov.repository.ApiCall
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MovieDetailActivity() : AppCompatActivity() {
    private lateinit var movie: MediaMovie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_layout)
        val intent = intent
        val idMovie = intent.getIntExtra("id", 1)

        GlobalScope.launch {
            movie = fetchMovieDetails(idMovie)
            runOnUiThread(Runnable {
                //TODO
                //Inflate data...
            })
        }
    }

    private fun fetchMovieDetails(idMovie: Int) : MediaMovie {
        return ApiCall().getMovieById(idMovie)
    }
}