package com.projetb3.movynov.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.projetb3.movynov.dataclasses.watchlist.WatchlistResult
import com.projetb3.movynov.repository.MovynovApiCall
import okhttp3.RequestBody.Companion.toRequestBody


class WatchlistModel {
    private val tmdbApi = "/api/watchlists"

    public fun getWatchlistByToken(token : String) : List<WatchlistResult> {
        val urlRequest = tmdbApi

        val response =  MovynovApiCall().executeGetWithAuthorization(urlRequest, token)
        val watchlistResult : List<WatchlistResult> = Gson().fromJson(response,
            object : TypeToken<List<WatchlistResult?>?>() {}.type)
        return watchlistResult
    }

    public fun isInWatchList(token : String, idMovie: Int): Boolean {
        val urlRequest = tmdbApi + "/$idMovie"

        val response =  MovynovApiCall().executeGetWithAuthorization(urlRequest, token)
        val watchlistResult : IsInWatchListResponse = Gson().fromJson(response, IsInWatchListResponse::class.java)
        return watchlistResult.message!!
    }

    public fun addToWatchList(token : String, idMovie: Int): Boolean {
        val urlRequest = tmdbApi

        val body = AddToWatchListQuery(idMovie, 1)
        val jsonBody = Gson().toJson(body)
        val requestBody = jsonBody.toRequestBody()

        val response =  MovynovApiCall().executePostWithAuthorization(urlRequest,requestBody, token)
        if (response.isSuccessful) {
            return true
        }
        return false
    }

    public fun removeFromWatchlist(token : String, idMovie: Int): Boolean {
        val urlRequest = tmdbApi + "/movies/$idMovie"

        val response =  MovynovApiCall().executeBodylessDeleteWithAuthorization(urlRequest, token)
        if (response.isSuccessful) {
            return true
        }
        return false
    }

    private data class IsInWatchListResponse(
        @SerializedName("message" ) var message : Boolean? = null
    )

    private data class AddToWatchListQuery(
        @SerializedName("id_media" ) var idMedia : Int? = null,
        @SerializedName("status"   ) var status  : Int? = null
    )

}