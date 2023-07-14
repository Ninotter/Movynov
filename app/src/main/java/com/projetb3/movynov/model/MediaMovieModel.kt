package com.projetb3.movynov.model

import com.google.gson.Gson
import com.projetb3.movynov.dataclasses.MediaMovie
import com.projetb3.movynov.dataclasses.MediaMovieList
import com.projetb3.movynov.repository.MovynovApiCall
import okhttp3.HttpUrl
import java.io.IOException

class MediaMovieModel {
    private val tmdbApi : String = "/api/tmdb/movies"

    public fun getPopularMovies() : List<MediaMovie> {
        val urlRequest = tmdbApi + "/popular"

        val response =  MovynovApiCall().executeGet(urlRequest);
        val mediaMovieList = Gson().fromJson(response, MediaMovieList::class.java)
        return mediaMovieList.results
    }

    public fun getMovieById(id : Int) : MediaMovie{
        val urlRequest = tmdbApi + "/$id"

        val originalResponse =  MovynovApiCall().executeGet(urlRequest);
        //somehow, the API returns [] instead of {} when there is no result
        //solution for this edge case
        var response = originalResponse.replace("[]", "{}")
        //TODO : Fix edge case where videos : [] instead of null

        val mediaMovie = Gson().fromJson(response, MediaMovie::class.java)
        return mediaMovie
    }

    public fun getRelatedMovies(id: Int) : List<MediaMovie> {
        val urlRequest = tmdbApi + "/recommandation/$id"

        val response = MovynovApiCall().executeGet(urlRequest);
        val mediaMovieList = Gson().fromJson(response, MediaMovieList::class.java)
        return mediaMovieList.results
    }

    public fun getMoviesByTitle(search : String) : List<MediaMovie>{
        val urlRequest = tmdbApi + "/searchName/$search"

        var response =  MovynovApiCall().executeGet(urlRequest);
        val mediaMovieList = Gson().fromJson(response, MediaMovieList::class.java)
        return mediaMovieList.results
    }
}