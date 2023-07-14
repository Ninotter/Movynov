package com.projetb3.movynov.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.projetb3.movynov.dataclasses.MediaMovieList
import com.projetb3.movynov.dataclasses.forum.ForumComment
import com.projetb3.movynov.dataclasses.forum.ForumPost
import com.projetb3.movynov.dataclasses.watchlist.WatchlistResult
import com.projetb3.movynov.repository.MovynovApiCall
import com.projetb3.movynov.ui.SpoilerText
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

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

    fun addPostToForumByMovie(idMovie : Int, title : String, content : String,  idUser : Int, tokenUser: String) : ForumPost?{
        val urlRequest = tmdbApi + "/posts"

        val containsSpoilers = SpoilerText().containsSpoiler(content)

        val requestBody : RequestBody = Gson().toJson(AddPostByMovieQuery(1, idUser, title, content, idMovie, containsSpoilers)).toRequestBody()

        val response =  MovynovApiCall().executePostWithAuthorization(urlRequest, requestBody, tokenUser);

        if (response.isSuccessful){
            val post : ForumPost = Gson().fromJson(response.body?.string(), object : TypeToken<ForumPost?>() {}.type)
            return post
        }
        return null
    }

    private data class AddPostByMovieQuery(
        @SerializedName("idForumCategory" ) var idForumCategory : Int,
        @SerializedName("idUser"          ) var idUser          : Int,
        @SerializedName("title"           ) var title           : String,
        @SerializedName("content"         ) var content         : String,
        @SerializedName("idMedia"         ) var idMedia         : Int,
        @SerializedName("spoilers"        ) var spoilers        : Boolean
    )

    fun addCommentToForumPost(idPost: Int, commentContent: String, idUser : Int, tokenUser: String) : Boolean{
        val urlRequest = tmdbApi + "/comments"

        val containsSpoilers = SpoilerText().containsSpoiler(commentContent)

        val requestBody : RequestBody = Gson().toJson(addCommentToPostQuery(idPost, commentContent, idUser, containsSpoilers)).toRequestBody()

        val response =  MovynovApiCall().executePostWithAuthorization(urlRequest, requestBody, tokenUser);

        return response.isSuccessful
    }

    private data class addCommentToPostQuery(
        @SerializedName("idForumPost" ) var idForumPost : Int,
        @SerializedName("content"     ) var content     : String,
        @SerializedName("idUser"      ) var idUser      : Int,
        @SerializedName("spoilers"    ) var spoilers    : Boolean
    )
}