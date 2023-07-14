package com.projetb3.movynov.dataclasses.forum

import com.google.gson.annotations.SerializedName
import com.projetb3.movynov.dataclasses.MediaMovie
import com.projetb3.movynov.dataclasses.auth.User
import com.projetb3.movynov.model.ForumModel
import com.projetb3.movynov.model.MediaMovieModel

class ForumPost(
    @SerializedName("id"              ) var id              : Int?             = null,
    @SerializedName("title"           ) var title           : String?          = null,
    @SerializedName("content"         ) var content         : String?          = null,
    @SerializedName("idForumCategory" ) var forumCategory   : ForumCategory? = ForumCategory(),
    @SerializedName("idMedia"         ) var idMedia         : Int?             = null,
    @SerializedName("idUser"          ) var user            : User?          = User(),
    @SerializedName("spoilers"        ) var spoilers        : Boolean?         = null,
    @SerializedName("uuid"            ) var uuid            : String?          = null,
    @SerializedName("createdAt"       ) var createdAt       : String?          = null,
    @SerializedName("updatedAt"       ) var updatedAt       : String?          = null
){
    var comments : List<ForumComment>? = listOf()
    var movie : MediaMovie? = null

    public fun updateComments(){
        if (this.id != null){
            this.comments = ForumModel().getAllCommentsByPostId(this.id!!)
        }
    }

    public fun updateMovie(){
        if (this.idMedia != null){
            this.movie = MediaMovieModel().getMovieById(this.idMedia!!)
        }
    }
}
