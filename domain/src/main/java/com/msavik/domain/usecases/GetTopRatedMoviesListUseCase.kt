package com.msavik.domain.usecases

import com.msavik.domain.model.movie.Movie
import com.msavik.domain.repository.MovieRepository

class GetTopRatedMoviesListUseCase(
    private val repository: MovieRepository
) : BaseUseCase<Unit?, List<Movie>>() {
    override suspend fun execute(params: Unit?): List<Movie> {
        return repository.getTopRatedMoviesList()
    }
}