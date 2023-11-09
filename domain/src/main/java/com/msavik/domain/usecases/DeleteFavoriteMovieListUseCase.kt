package com.msavik.domain.usecases

import com.msavik.domain.repository.MovieRepository

class DeleteFavoriteMovieListUseCase(
    private val repository: MovieRepository
) : BaseUseCase<Unit?, Unit>() {
    override suspend fun execute(params: Unit?) {
        repository.deleteAllFavoriteMoviesDatabase()
    }
}