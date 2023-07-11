package com.projetb3.movynov.activities.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.projetb3.movynov.R

class ForumCommentsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val forumCommentContent : TextView = itemView.findViewById<TextView>(R.id.forum_comment_content)
    val forumCommentAuthorAndDate : TextView = itemView.findViewById<TextView>(R.id.forum_comment_author_and_date)
}