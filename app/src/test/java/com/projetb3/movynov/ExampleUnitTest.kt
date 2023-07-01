package com.projetb3.movynov

import com.projetb3.movynov.dataclasses.MediaMovie
import com.projetb3.movynov.repository.ApiCall
import org.junit.Assert
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
        val movies : List<MediaMovie> = ApiCall().getPopularMovies()
        assertTrue(movies.isNotEmpty())
    }

    @Test
    fun Movie_isNotEmpty(){
        val movie : MediaMovie = ApiCall().getMovieById(550)
        assertTrue(movie.title != null)
    }
}