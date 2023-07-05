package com.projetb3.movynov

import com.projetb3.movynov.dataclasses.MediaMovie
import com.projetb3.movynov.model.AuthModel
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
    fun Login_isCorrect(){
        val response : Response = AuthModel().login("test2@gmail.com", "password")
        assertTrue(response.isSuccessful)
    }
}