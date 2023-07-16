package com.projetb3.movynov.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

        findViewById<Button>(R.id.forum_details_add_comment_button).setOnClickListener {
            if (viewModel.getConnectedUser() == null){
                Toast.makeText(this, "Vous devez être connecté pour ajouter un commentaire", Toast.LENGTH_LONG).show()
            }else{
                findViewById<Button>(R.id.forum_details_add_comment_button).visibility = Button.GONE
                findViewById<ConstraintLayout>(R.id.forum_details_add_comment_container).visibility = ConstraintLayout.VISIBLE
                findViewById<Button>(R.id.forum_details_add_comment_submit_button).setOnClickListener{
                    val commentContent = findViewById<android.widget.EditText>(R.id.forum_details_add_comment_edit_text).text.toString()
                    if (commentContent.isEmpty()){
                        Toast.makeText(this, "Vous devez écrire un commentaire", Toast.LENGTH_LONG).show()
                    }else{
                        addCommentToPost()
                    }
                }
            }
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
        var stringDate : String = LocalDate.parse(forumPost.createdAt!!.substring(0,10), DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(
            DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        forumPostAuthorAndDate.text = "Posté par ${forumPost.user!!.username} le ${stringDate}"
        forumPostMovieTitle.text = forumPost.movie!!.title

    }

    private fun inflateRecyclerComments() {
        val forumCommentsAdapter = ForumCommentsAdapter(forumPost.comments!!)
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.forum_details_comments_recycler_view)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.adapter = forumCommentsAdapter
    }

    private fun addCommentToPost(){
        GlobalScope.launch {
            ForumModel().addCommentToForumPost(forumPost.id!!, findViewById<EditText>(R.id.forum_details_add_comment_edit_text).text.toString(),viewModel.getConnectedUser()!!.id!! , viewModel.getConnectedUser()!!.token!!)
            runOnUiThread(Runnable {
                Toast.makeText(this@ForumDetailsActivity, "Commentaire ajouté", Toast.LENGTH_LONG).show()
            })
            forumPost.updateComments()
            runOnUiThread(Runnable {
                findViewById<android.widget.EditText>(R.id.forum_details_add_comment_edit_text).text.clear()
                findViewById<Button>(R.id.forum_details_add_comment_button).visibility = Button.VISIBLE
                findViewById<ConstraintLayout>(R.id.forum_details_add_comment_container).visibility = ConstraintLayout.GONE
                inflateRecyclerComments()
            })
        }
    }

    override fun onBackPressed() {
        finish()
    }
}