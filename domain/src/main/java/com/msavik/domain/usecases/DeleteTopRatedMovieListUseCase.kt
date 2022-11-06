package com.msavik.domain.usecases

import com.msavik.domain.repository.MovieRepository

class DeleteTopRatedMovieListUseCase(
    private val repository: MovieRepository
) : BaseUseCase<Unit?, Unit>() {
    override suspend fun execute(params: Unit?) {
        repository.deleteAllTopRatedMoviesDatabase()
    }
}