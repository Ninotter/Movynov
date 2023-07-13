package com.projetb3.movynov.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import com.projetb3.movynov.R
import com.projetb3.movynov.activities.adapters.ForumCommentsAdapter
import com.projetb3.movynov.activities.adapters.MovieListAdapter
import com.projetb3.movynov.dataclasses.auth.User
import com.projetb3.movynov.dataclasses.forum.ForumPost
import com.projetb3.movynov.model.ForumModel
import com.projetb3.movynov.ui.SpoilerText
import com.projetb3.movynov.viewmodels.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ForumDetailsActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var forumPost : ForumPost
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum_details)

        val idForumPost = intent.getIntExtra("idForumPost", -1)

        if (idForumPost == -1){
            finish()
        }

        GlobalScope.launch{
            try{
                forumPost = ForumModel().getForumPostById(idForumPost)
                forumPost.updateComments()
                forumPost.updateMovie()
            }catch (e : Exception){
                runOnUiThread(Runnable {
                    Toast.makeText(this@ForumDetailsActivity, "Erreur lors de la récupération du post", Toast.LENGTH_LONG).show()
                })
                finish()
            }

            runOnUiThread(Runnable {
                inflateData(viewModel.getConnectedUser())
                inflateRecyclerComments()
                findViewById<ProgressBar>(R.id.forum_details_progress_bar).visibility = ProgressBar.GONE
                findViewById<ConstraintLayout>(R.id.forum_details_layout_without_progress_bar).visibility = ConstraintLayout.VISIBLE
            })
        }
    }

    private fun inflateData(currentUser : User?) {
        val forumPostTitle = findViewById<android.widget.TextView>(R.id.forum_details_post_title)
        val forumPostContent = findViewById<android.widget.TextView>(R.id.forum_details_post_content)
        val forumPostAuthorAndDate = findViewById<android.widget.TextView>(R.id.forum_details_author_and_date)
        val forumPostMovieTitle = findViewById<android.widget.TextView>(R.id.forum_details_movie_title)

        forumPostTitle.text = forumPost.title
        try{
            SpoilerText().setSpoilerTextToTextView(forumPost.content!!, forumPostContent)
        }catch(ex : Exception){
            val text = forumPost.content!!.replace("||", "")
            forumPostContent.text = text
        }
        forumPostAuthorAndDate.text = "Posté par {$forumPost.user!!.username} le ${forumPost.createdAt.toString()}"
        forumPostMovieTitle.text = forumPost.movie!!.title

        if(currentUser != null){
            val forumPostAddComment = findViewById<android.widget.Button>(R.id.forum_details_add_comment_button)
            forumPostAddComment.setOnClickListener {
                addCommentToPost()
            }
        }
    }

    private fun inflateRecyclerComments() {
        val forumCommentsAdapter = ForumCommentsAdapter(forumPost.comments!!)
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.forum_details_comments_recycler_view)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.adapter = forumCommentsAdapter
    }

    private fun addCommentToPost(){
        //TODO
        //authmodel forum call
        //refresh()
    }

    override fun onBackPressed() {
        finish()
    }
}