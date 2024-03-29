package com.projetb3.movynov.activities.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projetb3.movynov.R
import com.projetb3.movynov.dataclasses.forum.ForumPost
import com.projetb3.movynov.ui.SpoilerText
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ForumPostsAdapter(
    private val forumPosts : List<ForumPost>,
    private val onItemClick: (forumPost: ForumPost) -> Unit
    ) : RecyclerView.Adapter<ForumPostsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumPostsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forum_post, parent, false)
        return ForumPostsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return forumPosts.size
    }

    override fun onBindViewHolder(holder: ForumPostsViewHolder, position: Int) {
        val forumPost = forumPosts[position]
        holder.forumPostTitle.text = forumPost.title
        holder.forumPostTitleMovie.text = forumPost.movie?.title
        try{
            SpoilerText().setSpoilerTextToTextView(forumPost.content!!, holder.forumPostContent)
        }
        catch(ex : Exception){
            val text = forumPost.content!!.replace("||", "")
            holder.forumPostContent.text = text
        }
        var stringDate : String = LocalDate.parse(forumPost.createdAt!!.substring(0,10), DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        holder.forumPostAuthorAndDate.text = "${forumPost.user?.username} - ${stringDate}"
        holder.forumPostMessageCount.text = "${forumPost.comments?.size} commentaires"
        if (forumPost.comments.isNullOrEmpty() || forumPost.comments?.last() == null){
            holder.forumLatestMessage.text = ""
        }else{
            var stringDateComments : String = LocalDate.parse(forumPost.comments?.last()!!.createdAt!!.substring(0,10), DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            holder.forumLatestMessage.text = "Dernier message le ${stringDateComments}"
        }
        holder.itemView.setOnClickListener {
            onItemClick(forumPost)
        }
    }
}