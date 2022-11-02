package com.msavik.domain.repository

import com.msavik.domain.model.movie.Movie
import com.msavik.domain.utility.Resource

interface MovieRepository {

    suspend fun getPopularMoviesList(): List<Movie>

    suspend fun getTopRatedMoviesList(): List<Movie>

    suspend fun getUpcomingMoviesList(): List<Movie>

    suspend fun getMovieById(id: Int): Movie
}