package com.msavik.domain.usecases

import com.msavik.domain.repository.MovieRepository

class UpdateTopRatedMoviesListUseCase(
    private val repository: MovieRepository
) : BaseUseCase<Unit?, Unit?>() {
    override suspend fun execute(params: Unit?) {
        val response = repository.getTopRatedMoviesList()
        repository.deleteAllTopRatedMoviesDatabase()
        for (movie in response) {
            repository.upsertTopRatedMovieDatabase(movie)
        }
    }
}