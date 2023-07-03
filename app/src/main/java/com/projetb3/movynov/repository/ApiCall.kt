package com.projetb3.movynov.repository

import com.google.gson.Gson
import com.projetb3.movynov.dataclasses.MediaMovie
import com.projetb3.movynov.dataclasses.MediaMovieList
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class ApiCall {
    private val baseUrl: String = "https://api.themoviedb.org/3/"
    private val apiKey = "api_key=fdfce6336a8fb018fbf3a502ae698cac"
    private val client = OkHttpClient()

    public fun getPopularMovies() : List<MediaMovie> {
        val request = Request.Builder()
            .url(baseUrl + "movie/popular?" + apiKey + "&language=fr-FR")
            .build()

        var mediaMovieList = MediaMovieList()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            mediaMovieList = Gson().fromJson(response.body!!.string(), MediaMovieList::class.java)
            return mediaMovieList.results
        }
    }

    public fun getMovieById(id : Int) : MediaMovie {
        val request = Request.Builder()
            .url(baseUrl + "movie/" + id + "?" + apiKey + "&language=fr-Fr")
            .build()

        var movie = MediaMovie()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            movie = Gson().fromJson(response.body!!.string(), MediaMovie::class.java)
            return movie
        }
    }

    public fun getMovieAndWatchProvidersById(id : Int) : MediaMovie {
        val request = Request.Builder()
            .url(baseUrl + "movie/" + id + "?" + apiKey + "&language=fr-Fr" + "&append_to_response=watch/providers")
            .build()

        var movie = MediaMovie()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            movie = Gson().fromJson(response.body!!.string(), MediaMovie::class.java)
            return movie
        }
    }

    public fun getMovieAndWatchProvidersAndCreditsById(id : Int) : MediaMovie {
        val request = Request.Builder()
            .url(baseUrl + "movie/" + id + "?" + apiKey + "&language=fr-Fr" + "&append_to_response=watch/providers,credits")
            .build()

        var movie = MediaMovie()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            movie = Gson().fromJson(response.body!!.string(), MediaMovie::class.java)
            return movie
        }
    }
}