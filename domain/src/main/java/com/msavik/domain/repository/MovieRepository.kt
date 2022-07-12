package com.msavik.domain.repository

import com.msavik.domain.model.movie.Movie
import com.msavik.domain.utility.Resource

interface MovieRepository {

    suspend fun getPopularMoviesList(): List<Movie>
}