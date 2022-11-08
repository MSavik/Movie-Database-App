package com.msavik.domain.usecases

import com.msavik.domain.model.movie.Movie
import com.msavik.domain.repository.MovieRepository

class DeleteFavoriteMovieUseCase(
    private val repository: MovieRepository
) : BaseUseCase<Movie?, Unit>() {
    override suspend fun execute(movie: Movie?) {
        if (movie != null) {
            repository.deleteFavoriteMovieDatabase(movie)
        }
    }
}