package com.msavik.domain.usecases

import com.msavik.domain.model.movie.Movie
import com.msavik.domain.repository.MovieRepository

class GetPopularMovieByIdUseCase(
    private val repository: MovieRepository
) : BaseUseCase<Int?, Movie>() {
    override suspend fun execute(id: Int?): Movie {
        val cachedMovie = repository.getPopularMovieByIdDatabase(id ?: 0)

        return if (cachedMovie.genres != null) {
            cachedMovie
        } else {
            val response = repository.getMovieById(id ?: 0)
            repository.upsertPopularMovieDatabase(response)
            response
        }
    }
}