package com.msavik.domain.usecases

import com.msavik.domain.model.movie.Movie
import com.msavik.domain.repository.MovieRepository

class GetPopularMoviesListUseCase(
    private val repository: MovieRepository
) : BaseUseCase<Unit?, List<Movie>>() {
    override suspend fun execute(params: Unit?): List<Movie> {
        val cachedMovieList = repository.getPopularMovieListDatabase()
        return if (cachedMovieList.isNotEmpty()) {
            cachedMovieList
        } else {
            val response = repository.getPopularMoviesList()
            for (movie in response) {
                repository.upsertPopularMovieDatabase(movie)
            }
            response
        }
    }
}