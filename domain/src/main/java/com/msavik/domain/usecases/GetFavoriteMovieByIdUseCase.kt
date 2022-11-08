package com.msavik.domain.usecases

import com.msavik.domain.model.movie.Movie
import com.msavik.domain.repository.MovieRepository

class GetFavoriteMovieByIdUseCase(
    private val repository: MovieRepository
) : BaseUseCase<Int?, Movie>() {
    override suspend fun execute(id: Int?): Movie {
        return repository.getFavoriteMovieByIdDatabase(id ?: 0)
    }
}