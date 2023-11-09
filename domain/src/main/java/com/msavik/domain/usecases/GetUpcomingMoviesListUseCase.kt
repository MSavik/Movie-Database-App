package com.msavik.domain.usecases

import com.msavik.domain.model.movie.Movie
import com.msavik.domain.repository.MovieRepository

class GetUpcomingMoviesListUseCase(
    private val repository: MovieRepository
) : BaseUseCase<Unit?, List<Movie>>() {
    override suspend fun execute(params: Unit?): List<Movie> {
        val cachedMovieList = repository.getUpcomingMovieListDatabase()
        return cachedMovieList.ifEmpty {
            val response = repository.getUpcomingMoviesList()
            for (movie in response) {
                repository.upsertUpcomingMovieDatabase(movie)
            }
            response
        }
    }
}