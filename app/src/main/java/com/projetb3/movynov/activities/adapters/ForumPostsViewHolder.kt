package com.projetb3.movynov.activities.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.projetb3.movynov.R

class ForumPostsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    val forumPostTitle : TextView = itemView.findViewById(R.id.title_forum_post)
    val forumPostTitleMovie : TextView = itemView.findViewById(R.id.movie_title_forum_post)
    val forumPostContent : TextView = itemView.findViewById(R.id.content_forum_post)
    val forumPostAuthorAndDate : TextView = itemView.findViewById(R.id.author_forum_post)
    val forumPostMessageCount : TextView = itemView.findViewById(R.id.message_count_forum_post)
    val forumLatestMessage : TextView = itemView.findViewById(R.id.latest_message_date_forum_post)
}