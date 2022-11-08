package com.msavik.domain.repository

import com.msavik.domain.model.movie.Movie
import com.msavik.domain.utility.Resource

interface MovieRepository {

    /* API calls */

    suspend fun getPopularMoviesList(): List<Movie>

    suspend fun getTopRatedMoviesList(): List<Movie>

    suspend fun getUpcomingMoviesList(): List<Movie>

    suspend fun getMovieById(id: Int): Movie

    suspend fun searchMovies(query: String): List<Movie>


    /* DB calls */

    /* Popular */
    suspend fun upsertPopularMovieDatabase(movie: Movie)

    fun getPopularMovieByIdDatabase(id: Int): Movie

    fun getPopularMovieListDatabase(): List<Movie>

    suspend fun deletePopularMovieDatabase(movie: Movie)

    suspend fun deleteAllPopularMoviesDatabase()

    /* TopRated */
    suspend fun upsertTopRatedMovieDatabase(movie: Movie)

    fun getTopRatedMovieByIdDatabase(id: Int): Movie

    fun getTopRatedMovieListDatabase(): List<Movie>

    suspend fun deleteTopRatedMovieDatabase(movie: Movie)

    suspend fun deleteAllTopRatedMoviesDatabase()

    /* Upcoming */
    suspend fun upsertUpcomingMovieDatabase(movie: Movie)

    fun getUpcomingMovieByIdDatabase(id: Int): Movie

    fun getUpcomingMovieListDatabase(): List<Movie>

    suspend fun deleteUpcomingMovieDatabase(movie: Movie)

    suspend fun deleteAllUpcomingMoviesDatabase()

    /* Favorite */
    suspend fun upsertFavoriteMovieDatabase(movie: Movie)

    fun getFavoriteMovieByIdDatabase(id: Int): Movie

    fun getFavoriteMovieListDatabase(): List<Movie>

    suspend fun deleteFavoriteMovieDatabase(movie: Movie)
}