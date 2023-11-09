package com.msavik.domain.usecases

import com.msavik.domain.repository.MovieRepository

class UpdateUpcomingMoviesListUseCase(
    private val repository: MovieRepository
) : BaseUseCase<Unit?, Unit?>() {
    override suspend fun execute(params: Unit?) {
        val response = repository.getUpcomingMoviesList()
        repository.deleteAllUpcomingMoviesDatabase()
        for (movie in response) {
            repository.upsertUpcomingMovieDatabase(movie)
        }
    }
}