package com.projetb3.movynov

import com.projetb3.movynov.dataclasses.MediaMovie
import com.projetb3.movynov.dataclasses.watchlist.WatchlistResult
import com.projetb3.movynov.model.AuthModel
import com.projetb3.movynov.model.ForumModel
import com.projetb3.movynov.model.MediaMovieModel
import com.projetb3.movynov.model.WatchlistModel
import com.projetb3.movynov.repository.tmdbDirectApiCall
import okhttp3.Response
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun MovieList_isNotEmpty(){
        val movies : List<MediaMovie> = tmdbDirectApiCall().getPopularMovies()
        assertTrue(movies.isNotEmpty())
    }

    @Test
    fun Movie_isNotEmpty(){
        val movie : MediaMovie = tmdbDirectApiCall().getMovieAndWatchProvidersAndCreditsAndVideosById(315162)
        assertTrue(movie.title != null)
    }

    @Test
    fun Recommanded_isNotEmpty(){
        val movies : List<MediaMovie> = tmdbDirectApiCall().getRelatedMoviesById(76600)
        assertTrue(movies.isNotEmpty())
    }

    @Test
    fun RecommandedMovynov_isNotEmpty(){
        val movies : List<MediaMovie> = MediaMovieModel().getRelatedMovies(76600)
        assertTrue(movies.isNotEmpty())
    }

    @Test
    fun Login_isCorrect(){
        val response : Response = AuthModel().login("test2@gmail.com", "password")
        assertTrue(response.isSuccessful)
    }

    @Test
    fun watchlist_isNotEmpty(){
        val results :List<WatchlistResult> = WatchlistModel().getWatchlistByToken("eyJpYXQiOjE2ODg2Mzc0MjAsImV4cCI6MTY4ODYzODMyMCwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInBzZXVkbyI6IlRlc3RldXIiLCJlbWFpbCI6InRlc3QyQGdtYWlsLmNvbSJ9")
        assertTrue(results.isNotEmpty())
    }

    @Test
    fun PostsAboutFilm_isNotEmpty(){
        val posts = ForumModel().getAllForumPostsByMovieId(122)
        assertTrue(posts.isNotEmpty())
    }

    @Test
    fun CommentsBelowPost_isNotEmpty(){
        val comments = ForumModel().getAllCommentsByPostId(1)
        assertTrue(comments.isNotEmpty())
    }

    @Test
    fun CommentsByUser_isNotEmpty(){
        val token = "eyJpYXQiOjE2ODg2NDU4ODYsImV4cCI6MTY4ODY0Njc4Niwicm9sZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJwc2V1ZG8iOiJUZXN0ZXVyIiwiZW1haWwiOiJ0ZXN0MkBnbWFpbC5jb20ifQ=="
        val comments = ForumModel().getAllCommentsByUserToken(token)
        assertTrue(comments.isNotEmpty())
    }
}