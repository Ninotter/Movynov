package com.projetb3.movynov.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.projetb3.movynov.dataclasses.MediaMovieList
import com.projetb3.movynov.dataclasses.forum.ForumComment
import com.projetb3.movynov.dataclasses.forum.ForumPost
import com.projetb3.movynov.dataclasses.watchlist.WatchlistResult
import com.projetb3.movynov.repository.MovynovApiCall

class ForumModel {
    private val tmdbApi = "/api/forums"
    public fun getAllForumPostsByMovieId(movieId : Int) : List<ForumPost> {
        val urlRequest = tmdbApi + "/posts/$movieId"

        val response =  MovynovApiCall().executeGet(urlRequest);
        val posts : List<ForumPost> = Gson().fromJson(response, object : TypeToken<List<ForumPost?>?>() {}.type)
        return posts
    }

    public fun getAllCommentsByUserToken(token: String) : List<ForumComment> {
        val urlRequest = tmdbApi + "/comments"

        val response =  MovynovApiCall().executeGetWithAuthorization(urlRequest, token);
        val comments : List<ForumComment> = Gson().fromJson(response, object : TypeToken<List<ForumComment?>?>() {}.type)
        return comments
    }

    public fun getAllCommentsByPostId(postId: Int) : List<ForumComment> {
        val urlRequest = tmdbApi + "/comments/$postId"

        val response =  MovynovApiCall().executeGet(urlRequest);
        val comments : List<ForumComment> = Gson().fromJson(response, object : TypeToken<List<ForumComment?>?>() {}.type)
        return comments
    }

    fun getForumPostById(idForumPost: Int): ForumPost {
        val urlRequest = tmdbApi + "/posts/one/$idForumPost"

        val response =  MovynovApiCall().executeGet(urlRequest);
        val post : ForumPost = Gson().fromJson(response, object : TypeToken<ForumPost?>() {}.type)
        return post
    }
}