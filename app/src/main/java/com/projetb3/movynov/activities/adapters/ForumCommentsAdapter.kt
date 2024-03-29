package com.projetb3.movynov.activities.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projetb3.movynov.R
import com.projetb3.movynov.dataclasses.forum.ForumComment
import com.projetb3.movynov.ui.SpoilerText
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
        try{
            SpoilerText().setSpoilerTextToTextView(forumComment.content!!, holder.forumCommentContent)
        }catch(ex : Exception){
            val text = forumComment.content!!.replace("||", "")
            holder.forumCommentContent.text = text
        }
        var stringDate : String = LocalDate.parse(forumComment.createdAt!!.substring(0,10), DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(
            DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        holder.forumCommentAuthorAndDate.text = "Commenté par " + forumComment.idUser?.username + " le " + stringDate
    }
}