package com.msavik.domain.usecases

import com.msavik.domain.model.movie.Movie
import com.msavik.domain.repository.MovieRepository

class GetTopRatedMovieByIdUseCase(
    private val repository: MovieRepository
) : BaseUseCase<Int?, Movie>() {
    override suspend fun execute(id: Int?): Movie {
        val cachedMovie = repository.getTopRatedMovieByIdDatabase(id ?: 0)

        return if (cachedMovie.genres != null) {
            cachedMovie
        } else {
            val response = repository.getMovieById(id ?: 0)
            repository.upsertTopRatedMovieDatabase(response)
            response
        }
    }
}