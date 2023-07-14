package com.projetb3.movynov.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.projetb3.movynov.R
import com.projetb3.movynov.activities.adapters.ForumPostsAdapter
import com.projetb3.movynov.dataclasses.auth.User
import com.projetb3.movynov.dataclasses.forum.ForumPost
import com.projetb3.movynov.model.ForumModel
import com.projetb3.movynov.viewmodels.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ForumActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout : androidx.drawerlayout.widget.DrawerLayout
    private val viewModel: MainViewModel by viewModels()
    private lateinit var forumPosts : List<ForumPost>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum)

        val idMovie = intent.getIntExtra("idMovie", -1)

        if(idMovie != -1) {
            GlobalScope.launch {
                forumPosts = ForumModel().getAllForumPostsByMovieId(idMovie)
                for (forumPost in forumPosts) {
                    forumPost.updateComments()
                    forumPost.updateMovie()
                }
                runOnUiThread(Runnable {
                    inflateData(viewModel.getConnectedUser())
                    inflateRecyclerView(forumPosts)
                    findViewById<ProgressBar>(R.id.forum_progress_bar).visibility = ProgressBar.GONE
                    findViewById<ConstraintLayout>(R.id.forum_layout_without_progress_bar).visibility = ConstraintLayout.VISIBLE
                })
            }
        }

        /**
         * Navigation Drawer
         */
        drawerLayout = findViewById(R.id.forum_drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.forum_nav_view)
        if (viewModel.getConnectedUser() != null){
            MenuBehavior().hideLoginMenu(navigationView, viewModel.getConnectedUser()!!)
        }else{
            MenuBehavior().menuForNoUser(navigationView)
        }
        DrawerBehavior().setDrawerOpenOnClick(drawerLayout, navigationView, this, findViewById(R.id.forum_toolbar))

    }

    private fun inflateData(currentUser: User?) {
        val addPostContainer : ConstraintLayout = findViewById(R.id.forum_add_post_container)
        val addPostButton : Button = findViewById(R.id.forum_add_post_button)
        val submitPostButton : Button = findViewById(R.id.forum_post_submit_button)

        if (currentUser != null){
            findViewById<Button>(R.id.forum_add_post_button).setOnClickListener {
                runOnUiThread(Runnable {
                    addPostContainer.visibility = ConstraintLayout.VISIBLE
                    addPostButton.visibility = Button.GONE
                })
            }
        }

        submitPostButton.setOnClickListener(){
            runOnUiThread(Runnable {
                addPostContainer.visibility = ConstraintLayout.GONE
                addPostButton.visibility = Button.VISIBLE
            })
            onClickAddPost()
        }
    }

    private fun inflateRecyclerView(listPosts : List<ForumPost>){
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.forum_recycler_view)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.adapter = ForumPostsAdapter(listPosts, ::onClickPost)
    }

    private fun onClickPost(forumPost : ForumPost){
        val intent = android.content.Intent(this, ForumDetailsActivity::class.java)
        intent.putExtra("idForumPost", forumPost.id)
        startActivity(intent)
    }

    private fun onClickAddPost(){
        val title = findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.forum_add_post_title_edit_text).text.toString()
        val content = findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.forum_add_post_content_edit_text).text.toString()
        val idMovie = intent.getIntExtra("idMovie", -1)


        GlobalScope.launch {
            val newPost = ForumModel().addPostToForumByMovie(idMovie, title, content, viewModel.getConnectedUser()!!.id!!, viewModel.getConnectedUser()!!.token!!)
            newPost?.updateComments()
            newPost?.updateMovie()
            runOnUiThread(Runnable {
                if (newPost == null){
                    Toast.makeText(this@ForumActivity, "Erreur lors de l'ajout du post", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this@ForumActivity, "Post ajouté", Toast.LENGTH_SHORT).show()
                    forumPosts = forumPosts.plus(newPost)
                    inflateRecyclerView(forumPosts)
                    val addPostButton = findViewById<Button>(R.id.forum_add_post_button)
                    val addPostContainer = findViewById<ConstraintLayout>(R.id.forum_add_post_container)
                    val editTextTitle = findViewById<EditText>(R.id.forum_add_post_title_edit_text)
                    val editTextContent = findViewById<EditText>(R.id.forum_add_post_content_edit_text)
                    editTextTitle.text.clear()
                    editTextContent.text.clear()
                    addPostContainer.visibility = ConstraintLayout.GONE
                    addPostButton.visibility = Button.VISIBLE
                    findViewById<RecyclerView>(R.id.forum_recycler_view).scrollToPosition(forumPosts.size - 1)

                }
            })
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return MenuBehavior().onNavigationItemSelected(item, this)
    }
}