package com.projetb3.movynov.model

import android.provider.MediaStore.Video
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.projetb3.movynov.dataclasses.watchlist.WatchlistResult
import com.projetb3.movynov.repository.MovynovApiCall


class WatchlistModel {
    private val tmdbApi = "/api/watchlists"

    public fun getWatchlistByToken(token : String) : List<WatchlistResult> {
        val urlRequest = tmdbApi

        val response =  MovynovApiCall().executeGetWithAuthorization(urlRequest, token)
        val watchlistResult : List<WatchlistResult> = Gson().fromJson(response,
            object : TypeToken<List<WatchlistResult?>?>() {}.type)
        return watchlistResult
    }

}