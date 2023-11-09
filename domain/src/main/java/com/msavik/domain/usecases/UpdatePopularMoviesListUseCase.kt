package com.msavik.domain.usecases

import com.msavik.domain.repository.MovieRepository

class UpdatePopularMoviesListUseCase(
    private val repository: MovieRepository
) : BaseUseCase<Unit?, Unit?>() {
    override suspend fun execute(params: Unit?) {
        val response = repository.getPopularMoviesList()
        repository.deleteAllPopularMoviesDatabase()
        for (movie in response) {
            repository.upsertPopularMovieDatabase(movie)
        }
    }
}