package com.msavik.domain.usecases

import com.msavik.domain.model.movie.Movie
import com.msavik.domain.repository.MovieRepository

class SearchMoviesUseCase(
    private val repository: MovieRepository
) : BaseUseCase<String?, List<Movie>>() {
    override suspend fun execute(params: String?): List<Movie> {
        return repository.searchMovies(params ?: "")
    }
}