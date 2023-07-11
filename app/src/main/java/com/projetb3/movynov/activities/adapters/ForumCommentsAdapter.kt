package com.projetb3.movynov.activities.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projetb3.movynov.R
import com.projetb3.movynov.dataclasses.forum.ForumComment

class ForumCommentsAdapter(private var forumComments : List<ForumComment>) : RecyclerView.Adapter<ForumCommentsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumCommentsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forum_comment, parent, false)
        return ForumCommentsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return forumComments.size
    }

    override fun onBindViewHolder(holder: ForumCommentsViewHolder, position: Int) {
        val forumComment = forumComments[position]
        holder.forumCommentContent.text = forumComment.content
        holder.forumCommentAuthorAndDate.text = "Commenté par " + forumComment.idUser?.username + " le " + forumComment.createdAt.toString()
    }
}