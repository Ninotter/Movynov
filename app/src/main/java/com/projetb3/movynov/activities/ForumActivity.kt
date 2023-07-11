package com.projetb3.movynov.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import com.google.android.material.navigation.NavigationView
import com.projetb3.movynov.R
import com.projetb3.movynov.activities.adapters.ForumPostsAdapter
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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return MenuBehavior().onNavigationItemSelected(item, this)
    }
}