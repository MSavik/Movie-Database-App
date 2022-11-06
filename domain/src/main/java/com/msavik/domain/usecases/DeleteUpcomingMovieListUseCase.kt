package com.msavik.domain.usecases

import com.msavik.domain.repository.MovieRepository

class DeleteUpcomingMovieListUseCase(
    private val repository: MovieRepository
) : BaseUseCase<Unit?, Unit>() {
    override suspend fun execute(params: Unit?) {
        repository.deleteAllUpcomingMoviesDatabase()
    }
}